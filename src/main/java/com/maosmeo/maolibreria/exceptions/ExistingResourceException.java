package com.maosmeo.maolibreria.exceptions;

public class ExistingResourceException extends RuntimeException{
    public ExistingResourceException(String errorMessage) {
        super(errorMessage);
    }
}
