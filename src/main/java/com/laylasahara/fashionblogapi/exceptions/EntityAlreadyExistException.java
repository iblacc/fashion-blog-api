package com.laylasahara.fashionblogapi.exceptions;

public class EntityAlreadyExistException extends RuntimeException {
    private String message;

    public EntityAlreadyExistException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
