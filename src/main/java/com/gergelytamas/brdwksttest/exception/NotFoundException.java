package com.gergelytamas.brdwksttest.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
