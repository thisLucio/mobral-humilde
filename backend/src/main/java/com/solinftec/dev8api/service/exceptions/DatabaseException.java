package com.solinftec.dev8api.service.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg) {
        super(String.format("Database exception: %s", msg));
    }
}

