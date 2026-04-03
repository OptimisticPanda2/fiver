package com.example.fiver;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> excpeptionHandler(Exception ex)
    {
        return ResponseEntity.status(500).body(ex.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
      public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex)
    {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> handleValidation(MethodArgumentNotValidException ex) {

        HashMap<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(400).body(errors);
    }
}
