package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.*;
import model.login.AuthRequest;
import model.login.AuthResponse;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.Base64;
import java.util.stream.Collectors;

public class Client {

    private static final String BASE_URL      = "http://localhost:8080";
    private static final String AUTH_REFRESH  = "/refresh-token";
    private static final String AUTH_ME       = "/employees/me";

    private final OkHttpClient client;
    private final ObjectMapper om;

    // Volatile : Her thread'e göre değeri modify edilir
    // Token /Me cache
    private volatile String accessToken;
    private volatile String refreshToken;
    private volatile DtoEmployee meCache;

    public Client() {
        this.om = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules();

        // Authorization header’ını ekleyen interceptor
        Interceptor authHeader = chain -> {
            Request orig = chain.request();
            Request.Builder b = orig.newBuilder()
                    .header("Accept", "application/json");
            if (accessToken != null) {
                b.header("Authorization", "Bearer " + accessToken);
            }
            return chain.proceed(b.build());
        };

        // AccessToken expire olduğunda refresh deneyip yeni token'la tekrar dener.
        Authenticator bearerAuthenticator = (route, response) -> {
            if (responseCount(response) >= 2) return null; // 1'den fazla deneme yapılmasın
            synchronized (this) {
                if (tryRefreshBlocking()) {
                    return response.request().newBuilder() // tekrar istek oluşturuyoruz
                            .header("Authorization", "Bearer " + accessToken) // yeni token ile
                            .build();
                }
                return null; // refresh başarısız 401 - UNAUTHORIZED
            }
        };

        this.client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(20))
                .writeTimeout(Duration.ofSeconds(20))
                .addInterceptor(authHeader)
                .authenticator(bearerAuthenticator)
                .build();
    }

    public boolean login(String endpoint, AuthRequest request) throws IOException {
        String json = om.writeValueAsString(request);

        Request newReq = new Request.Builder()
                .url(BASE_URL + endpoint) // http://localhost:8080/login
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(newReq).execute()) {
            if (response.isSuccessful()) { // 2xx
                AuthResponse authResponse = om.readValue(response.body().string(), AuthResponse.class);
                this.accessToken  = authResponse.getAccessToken();  // new accessToken
                this.refreshToken = authResponse.getRefreshToken(); // new refreshToken
                this.meCache = null;
                return true;
            } else {
                debug("login failed", response);
                return false;
            }
        }
    }

    public DtoUser register(String endpoint, DtoUserIU dtoUserIU) throws JsonProcessingException {
        String json = om.writeValueAsString(dtoUserIU);

        Request newReq = new Request.Builder()
                .url(BASE_URL + endpoint) // http://localhost:8080/register
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(newReq).execute()) {
            int sc = response.code();
            String responseBody = response.body() != null ? response.body().string() : "";

            if (sc == 201 || sc == 200){ return om.readValue(responseBody, DtoUser.class); }

            if(sc == 409) { throw new IllegalStateException("Email zaten kayıtlı"); }

            String message = extractSpringErrorMessage(responseBody);
            throw new IOException("Register failed (HTTP " + sc + "): " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Basit hata ayıklama – Spring Boot error/ProblemDetail JSON'undan mesaj çekmeye çalışır
    private String extractSpringErrorMessage(String body) {
        try {
            Map<?,?> m = om.readValue(body, Map.class);
            Object message = m.get("message");
            if (message != null) return String.valueOf(message);
            Object detail = m.get("detail"); // ProblemDetail
            if (detail != null) return String.valueOf(detail);
        } catch (Exception ignore) {}
        return body;
    }

    // refresh endpoint’ine POST atıp yeni access token alma
    public synchronized boolean tryRefreshBlocking() {
        if (refreshToken == null) return false;
        try {
            OkHttpClient refreshHttp = new OkHttpClient(); // döngüye girmemesi için authenticator'sız client

            Map<String,String> payload = Map.of("refreshToken", refreshToken);
            String json = om.writeValueAsString(payload);

            Request request = new Request.Builder()
                    .url(BASE_URL + AUTH_REFRESH) // http://localhost:8080/refresh-token
                    .post(RequestBody.create(json, MediaType.parse("application/json")))
                    .build();

            try (Response resp = refreshHttp.newCall(request).execute()) {
                if (resp.isSuccessful()) { // 2xx
                    AuthResponse response = om.readValue(resp.body().string(), AuthResponse.class);
                    this.accessToken  = response.getAccessToken();
                    if (response.getRefreshToken() != null && !response.getRefreshToken().isBlank()) {
                        this.refreshToken = response.getRefreshToken();
                    }
                    this.meCache = null;
                    return true;
                } else {
                    debug("refresh failed", resp);
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("[API] refresh exception: " + e.getMessage());
            return false;
        }
    }

    // PRODUCTS

    public List<DtoProduct> listProducts(String endpoint) throws IOException {
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint)
                .get()
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.isSuccessful()) {
                DtoProduct[] arr = om.readValue(resp.body().string(), DtoProduct[].class);
                return Arrays.asList(arr);
            }
            debug("listProducts failed", resp);
            throw new IOException("listProducts HTTP " + resp.code());
        }
    }

    public boolean addProduct(String endpoint, DtoProductIU dtoProductIU) throws IOException {
        String json = om.writeValueAsString(dtoProductIU);
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.code() == 200 || resp.code() == 201) return true;
            debug("addProduct failed", resp);
            return false;
        }
    }

    public boolean updateProduct(String endpoint,String barcode, DtoProduct p) throws IOException {
        String json = om.writeValueAsString(p);
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint + "/" + url(barcode))
                .put(RequestBody.create(json, MediaType.parse("application/json")))
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.code() == 200) return true;
            debug("updateProduct failed", resp);
            return false;
        }
    }

    // EMPLOYEES

    public DtoEmployee me(String endpoint) throws IOException {
        if (meCache != null) return meCache;

        Request req = new Request.Builder()
                .url(BASE_URL + endpoint)
                .get()
                .build();

        try (Response resp = client.newCall(req).execute()) {
            if (resp.isSuccessful()) {
                DtoEmployee dto = om.readValue(resp.body().string(), DtoEmployee.class);
                this.meCache = dto;
                return dto;
            } else if (resp.code() == 401) {
                throw new IllegalStateException("Oturum süresi doldu. Tekrar giriş yapın.");
            }
            throw new IllegalStateException("Me sorgusu başarısız: HTTP " + resp.code());
        }
    }


    public List<DtoEmployee> filterEmployees(String endpoint) throws IOException {
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint) // http://localhost:8080/employees/filter/
                .get()
                .build();

        try (Response resp = client.newCall(req).execute()) {
            if (resp.isSuccessful()) {
                DtoEmployee[] arr = om.readValue(resp.body().string(), DtoEmployee[].class);
                return Arrays.asList(arr);
            }
            debug("listEmployees failed", resp);
            throw new IOException("listEmployees HTTP " + resp.code());
        }
    }

    public boolean updateEmployee(String endpoint,String barcode, DtoEmployee p) throws IOException {
        String json = om.writeValueAsString(p);
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint + "/" + url(barcode))
                .put(RequestBody.create(json, MediaType.parse("application/json")))
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.code() == 200) return true;
            debug("updateEmployee failed", resp);
            return false;
        }
    }

    public <T> T getById(String endpoint, long id, Class<T> type) throws IOException {
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint + "/" + id)
                .get()
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.isSuccessful()) {
                return om.readValue(resp.body().string(), type);
            }
            debug("getById failed", resp);
            throw new IOException("getById HTTP " + resp.code());
        }
    }

    public boolean deleteById(String endpoint, long id) throws IOException {
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint + "/" + id)
                .delete()
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.code() == 200 || resp.code() == 204) return true;
            debug("deleteById failed", resp);
            return false;
        }
    }

    public <T> boolean putById(String endpoint, long id, T dto) throws IOException {
        String json = om.writeValueAsString(dto);
        Request req = new Request.Builder()
                .url(BASE_URL + endpoint + "/" + id)
                .put(RequestBody.create(json, MediaType.parse("application/json")))
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.isSuccessful()) return true;
            debug("putById failed", resp);
            return false;
        }
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) result++;
        return result;
    }

    private static String url(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    @SuppressWarnings("unchecked")
    public Map<String,Object> parseJwtClaims() {
        if (accessToken == null) return Map.of();
        try {
            String[] parts = accessToken.split("\\.");
            if (parts.length < 2) return Map.of();
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            return om.readValue(payload, new TypeReference<Map<String,Object>>(){});
        } catch (Exception e) {
            return Map.of();
        }
    }

    private static boolean claimsContainIgnoreCase(Map<String,Object> claims, List<String> keys, String expectedLower) {
        for (String k : keys) {
            Object v = claims.get(k);
            if (v instanceof Collection<?> col) {
                for (Object o : col) {
                    if (o != null && expectedLower.equalsIgnoreCase(String.valueOf(o))) return true;
                }
            } else if (v instanceof String s) {
                for (String token : s.split("[,\\s]+")) {
                    if (!token.isBlank() && expectedLower.equalsIgnoreCase(token)) return true;
                }
            }
        }
        return false;
    }

    private static void debug(String what, Response resp) {
        System.err.println("[API] " + what + " → " + resp.code());
        try {
            if (resp.body() != null) {
                String body = resp.peekBody(Long.MAX_VALUE).string();
                if (!body.isBlank()) System.err.println(body);
            }
        } catch (Exception ignored) {}
    }

    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }

    private static Set<String> toStringSet(Object v) {
        if (v == null) return Set.of();
        if (v instanceof Collection<?> c) {
            return c.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.toSet());
        }
        if (v instanceof String s) { // "ROLE_USER ROLE_ADMIN" gibi tek string de olabilir
            String[] parts = s.trim().split("[,\\s]+");
            return Arrays.stream(parts).filter(p -> !p.isBlank()).collect(Collectors.toSet());
        }
        return Set.of(v.toString());
    }

    public Set<String> getRolesFromToken() {
        Map<String,Object> c = parseJwtClaims();
        Set<String> out = new java.util.HashSet<>();

        Object single = c.get("role");
        if (single != null) {
            String role = single.toString().toUpperCase(Locale.ROOT);
            if (!role.isBlank()) {
                out.add(role);                // "ADMIN"
                //out.add("ROLE_" + role);    // "ROLE_ADMIN"
            }
        }

        return out;
    }

    public Set<String> getPermissionsFromToken() {
        Map<String,Object> c = parseJwtClaims();
        Object p = c.get("permissions");
        return toStringSet(p);
    }

    public boolean hasRole(String role) {
        return getRolesFromToken().contains(role);
    }

    public boolean hasAnyRole(String... roles) {
        Set<String> set = getRolesFromToken();
        for (String r: roles) if (set.contains(r)) return true;
        return false;
    }

    public boolean hasPermission(String perm) {
        return getPermissionsFromToken().contains(perm);
    }

    public boolean hasAllPermissions(String... perms) {
        Set<String> set = getPermissionsFromToken();
        for (String p: perms) if (!set.contains(p)) return false;
        return true;
    }
}
