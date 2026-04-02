package com.example.fiver;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

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
}
