package com.example.transportapi.exception;

import com.example.transportapi.payload.ApiResponse;
import com.example.transportapi.payload.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInput(InvalidInputException e){
        ApiResponse res = new ApiResponse();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setMessage(e.getMessage());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException e){
        ApiResponse res = ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentEx(MethodArgumentNotValidException e){
        ErrorResponse res = new ErrorResponse();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        res.setMessage("Invalid method arguments were provided.");
        res.setErrors(allErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsEx(BadCredentialsException e){
        ErrorResponse res = new ErrorResponse();
        res.setStatus(HttpStatus.FORBIDDEN.value());
        res.setMessage("Forbidden");
        res.setErrors(List.of("Username/password combination is incorrect."));
        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDenied(AccessDeniedException e){
        ApiResponse res = ApiResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Access is denied.")
                .build();
        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }

}
