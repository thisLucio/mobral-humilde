package com.solinftec.dev8api.service.exceptions;

public class ResourceException extends RuntimeException {

    public ResourceException(Long id, String msg) {
        super(String.format("Resource %d raised an exception: %s", id, msg));
    }
}