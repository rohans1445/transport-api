package com.example.transportapi.exception;

import com.example.transportapi.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInput(InvalidInputException e){
        ApiResponse res = new ApiResponse();
        res.setStatus(HttpStatus.BAD_REQUEST.name());
        res.setMessage(e.getMessage());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

}
