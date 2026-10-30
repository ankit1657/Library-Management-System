package com.LearningSpring.LearningAPI.Exception;


import com.LearningSpring.LearningAPI.dto.ErrorResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {


    // Jab koi record/resource na mile (404 NOT FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)

    public ResponseEntity<ErrorResDto> handlerResourcNotFound(ResourceNotFoundException ex){


        ErrorResDto error = new ErrorResDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                ex.getMessage()
        );
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

   // Koi bhi unhandled ya internal error aaye (500 INTERNAL SERVER ERROR)

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResDto> handleGlobleException(Exception ex){
          ErrorResDto error = new ErrorResDto(

                  LocalDateTime.now(),
                  HttpStatus.INTERNAL_SERVER_ERROR.value(),
                  "Interval Server Error",
                  ex.getMessage()
          );
          return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Validation Errors (@Valid fail hone par) -> Field-wise errors map return karega (400 BAD REQUEST)

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                err ->errors.put(err.getField(),err.getDefaultMessage())
        );

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }



    // Galat Username ya Password ke liye (401 UNAUTHORIZED)
    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResDto> handleBadCredentials(org.springframework.security.authentication.BadCredentialsException ex) {
        ErrorResDto error = new ErrorResDto(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Invalid Username or Password!" // Saaf message
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
