package com.gergelytamas.brdwksttest.exception;

public class MissingIdException extends RuntimeException {

    public MissingIdException() {}

    public MissingIdException(final String message) {
        super(message);
    }
}
