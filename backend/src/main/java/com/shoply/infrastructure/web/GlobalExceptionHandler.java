package com.shoply.infrastructure.web;
import com.shoply.infrastructure.web.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.NoSuchElementException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice public class GlobalExceptionHandler {
  @ExceptionHandler(NoSuchElementException.class) ResponseEntity<ApiError> missing(NoSuchElementException e,HttpServletRequest r){return response(HttpStatus.NOT_FOUND,e.getMessage(),r);}
  @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ApiError> invalid(IllegalArgumentException e,HttpServletRequest r){return response(HttpStatus.BAD_REQUEST,e.getMessage(),r);}
  private ResponseEntity<ApiError> response(HttpStatus s,String m,HttpServletRequest r){return ResponseEntity.status(s).body(new ApiError(Instant.now(),s.value(),s.getReasonPhrase(),m,r.getRequestURI()));}
}
