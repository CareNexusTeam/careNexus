package com.cts.careNexus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage());

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {

                    String field =
                            ((FieldError) error)
                                    .getField();

                    String message =
                            error.getDefaultMessage();

                    errors.put(field, message);
                });

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                ex.getMessage());

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(DrugExpiredException.class)
    public ResponseEntity<ErrorResponse> handleDrugExpiredException(RuntimeException e)

    {
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Bad Request",e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClaimExceedAmountException.class)
    public ResponseEntity<ErrorResponse> handleClaimExceedAmountException(ClaimExceedAmountException e)
    {
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Bad Request",e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException e)
    {
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Bad Response",e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}