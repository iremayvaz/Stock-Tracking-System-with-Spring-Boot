package com.iremayvaz.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice // @RestController sınıfları için global exception yakalayıcı tanımlar.
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    // Mesela eğer herhangi bir yerde throw new IllegalArgumentException() yapılırsa bu metoda düşer.
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    // Yanlış URL çağrıldığında fırlatılır
    public ResponseEntity<String> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
                .body("Hatalı endpoint: " + ex.getRequestURL());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    // Parametre olarak dosya göndermezse
    public ResponseEntity<String> handleMissingPart(MissingServletRequestPartException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Eksik dosya: " + ex.getRequestPartName() + " parametresi zorunludur.");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    // Yanlış content-type ile gönderirse
    public ResponseEntity<String> handleUnsupportedMedia(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Geçersiz Content-Type. Lütfen 'multipart/form-data' kullanın.");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleConflict(ResponseStatusException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Zaten kayıtlı veri" + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    // Yukarıdaki metodlarda yakalanmayan tüm hatalar buraya düşer.
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Bir hata oluştu: " + ex.getMessage());
    }
}
