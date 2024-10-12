package com.maosmeo.maolibreria.exceptions;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String errorMessage) {
        super(errorMessage);
    }
}
