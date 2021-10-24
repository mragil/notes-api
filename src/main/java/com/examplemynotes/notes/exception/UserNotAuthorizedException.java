package com.examplemynotes.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException(String message) {
        super(message);
    }


}
