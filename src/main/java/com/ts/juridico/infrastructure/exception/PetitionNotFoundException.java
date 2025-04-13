package com.ts.juridico.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PetitionNotFoundException extends RuntimeException {

    public PetitionNotFoundException(String message) {
        super(message);
    }
}