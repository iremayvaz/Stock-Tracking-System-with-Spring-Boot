import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SearchEngineAPI {

    private static final String BASE_URL = "http://localhost:8080/search";
    private final OkHttpClient client = new OkHttpClient();

    private String sendGetRequest(String content) throws IOException {
        String request_url = BASE_URL + "/" // http://localhost:8080/search
                                + content; // searchTheWordInFile : /{word}
                                           // traverseTheTree     : /traverse/{typeOfTraverse}

        Request request = new Request.Builder() // Yeni bir istek oluşturucu oluşturur.
                .url(request_url) // hangi adrese istek atacağım?
                .get() // GET isteği
                .build(); // HTTP isteği

        try (Response response // Backend'den request'e gelen cevap
                     = client.newCall(request) // request çalıştırılıyor
                .execute()) // senkron çalışıyor cevap gelene kadar bekler
        {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                String errorBody = response.body() != null ? response.body().string() : "";
                return "Error: " + response.code() + " - " + response.message() +
                        (errorBody.isEmpty() ? "" : "\nDetails: " + errorBody);
            }
        } catch (IOException e){
            return "Exception: " + e.getMessage();
        }
    }

    private String sendPostRequest(String endpoint,       // endpoint
                                   String paramName, // form-data key (selectedFiles / ignoreFile)
                                   File... files)    // tek ya da çoklu dosya (varargs (variable arguments)
                                                     // ; 0, 1 veya birden fazla argümanı kabul edebilir.)
            throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for(File file : files){
            MediaType mediaType = MediaType.parse(Files.probeContentType(file.toPath()));

            builder.addFormDataPart(
                    paramName,
                    file.getName(),
                    RequestBody.create(file, mediaType != null ? mediaType : MediaType.parse("application/octet-stream"))
            );
        }

        String request_url = BASE_URL + endpoint;

        Request request = new Request.Builder()
                .url(request_url)
                .post(builder.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                String errorBody = response.body() != null ? response.body().string() : "";
                return "Error: " + response.code() + " - " + response.message() +
                        (errorBody.isEmpty() ? "" : "\nDetails: " + errorBody);
            }

        } catch (IOException e){
            return "Exception: " + e.getMessage();
        }
    }

    public String loadFileToBST(File[] selectedFiles) throws IOException {
        return sendPostRequest("/in/bst", "selectedFiles", selectedFiles);
    }

    public String readIgnoreWords(File ignoreFile) throws IOException {
        return sendPostRequest("/without/ignoreList", "ignoreFile", ignoreFile);
    }

    public String searchTheWordInFile(String word) throws IOException {
        return sendGetRequest(word);
    }

    public String traverseTheTree(String typeOfTraverse) throws IOException {
        return sendGetRequest("traverse/" + typeOfTraverse);
    }
}
