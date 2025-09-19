package com.iremayvaz.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import java.time.Instant;
import java.util.Map;

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

    // Aynı veriye ait kayıt varsa : ÇAKIŞMA
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleConflict(ResponseStatusException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Zaten kayıtlı veri" + ex.getMessage());
    }

    // Erişim izni yoksa
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest req) {

        Map<String, Object> body = Map.<String, Object>of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.FORBIDDEN.value(),
                "error", HttpStatus.FORBIDDEN.getReasonPhrase(),
                "message", "Access Denied",
                "path", req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    // Doğrulanmadıysa
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(
            AuthenticationException ex, HttpServletRequest req) {

        Map<String, Object> body = Map.<String, Object>of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "message", ex.getMessage(),
                "path", req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(Exception.class)
    // Yukarıdaki metodlarda yakalanmayan tüm hatalar buraya düşer.
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Bir hata oluştu: " + ex.getMessage());
    }
}
