package com.examplemynotes.notes.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException exception,
            WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<Object> handleUserNotAuthorized(UserNotAuthorizedException exception,
            WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails, HttpStatus.UNAUTHORIZED);
    }


}
