package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.DtoEmployee;
import model.dto.DtoProduct;
import model.dto.DtoUser;
import model.dto.DtoUserIU;
import model.login.AuthRequest;
import model.login.AuthResponse;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.Base64;

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

    /* ================= AUTH ================= */

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

    /** /auth/me → DtoEmployee (rol adı: roleName) */
    public DtoEmployee me() throws IOException {
        if (meCache != null) return meCache;

        Request request = new Request.Builder()
                .url(baseUrl + AUTH_ME)
                .get()
                .build();

        try (Response resp = http.newCall(request).execute()) {
            if (resp.isSuccessful()) {
                meCache = om.readValue(resp.body().string(), DtoEmployee.class);
                return meCache;
            }
            debug("me() failed", resp);
            throw new IOException("Unauthorized: " + resp.code());
        }
    }

    /* ================= ROLE / PERMISSION ================= */

    /** Tek rol kullanıyorsun: DtoEmployee.roleName veya JWT claim fallback */
    public boolean hasRole(String expected) {
        if (expected == null) return false;
        String needle = expected.toLowerCase(Locale.ROOT);

        // 1) DTO üzerinden
        try {
            DtoEmployee u = me();
            if (u.getRoleName() != null && u.getRoleName().equalsIgnoreCase(expected)) return true;
        } catch (Exception ignore) {}

        // 2) JWT claim fallback (role/roles/authorities)
        Map<String,Object> claims = parseJwtClaims();
        return claimsContainIgnoreCase(claims, List.of("role","roles","authorities"), needle);
    }

    /** DTO’da izin listesi yoksa JWT’den (authorities/permissions/scope) bakar */
    public boolean hasPermission(String perm) {
        if (perm == null) return false;
        Map<String,Object> claims = parseJwtClaims();
        return claimsContainIgnoreCase(claims, List.of("permissions","authorities","scope"), perm.toLowerCase(Locale.ROOT));
    }

    /* ============== PRODUCTS ================= */

    public List<DtoProduct> listProducts() throws IOException {
        Request req = new Request.Builder().url(baseUrl + PRODUCTS).get().build();
        try (Response resp = http.newCall(req).execute()) {
            if (resp.isSuccessful()) {
                DtoProduct[] arr = om.readValue(resp.body().string(), DtoProduct[].class);
                return Arrays.asList(arr);
            }
            debug("listProducts failed", resp);
            throw new IOException("listProducts HTTP " + resp.code());
        }
    }

    public boolean addProduct(DtoProduct p) throws IOException {
        String json = om.writeValueAsString(p);
        Request req = new Request.Builder()
                .url(baseUrl + PRODUCTS)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();
        try (Response resp = http.newCall(req).execute()) {
            if (resp.code() == 200 || resp.code() == 201) return true;
            debug("addProduct failed", resp);
            return false;
        }
    }

    public boolean updateProduct(String barcode, DtoProduct p) throws IOException {
        String json = om.writeValueAsString(p);
        Request req = new Request.Builder()
                .url(baseUrl + PRODUCTS + "/" + url(barcode))
                .put(RequestBody.create(json, MediaType.parse("application/json")))
                .build();
        try (Response resp = http.newCall(req).execute()) {
            if (resp.code() == 200) return true;
            debug("updateProduct failed", resp);
            return false;
        }
    }

    public boolean deleteProduct(String barcode) throws IOException {
        Request req = new Request.Builder()
                .url(baseUrl + PRODUCTS + "/" + url(barcode))
                .delete()
                .build();
        try (Response resp = http.newCall(req).execute()) {
            if (resp.code() == 200 || resp.code() == 204) return true;
            debug("deleteProduct failed", resp);
            return false;
        }
    }

    /* ============== yardımcılar ============== */

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
            return om.readValue(payload, Map.class);
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

    /* Gerekirse dışarı ver */
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
}
