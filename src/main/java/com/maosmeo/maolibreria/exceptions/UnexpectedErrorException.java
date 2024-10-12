package com.maosmeo.maolibreria.exceptions;

public class UnexpectedErrorException extends RuntimeException{
    public UnexpectedErrorException(String errorMessage) {
        super(errorMessage);
    }
}
