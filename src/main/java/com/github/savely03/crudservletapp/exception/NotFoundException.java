package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.core.Response;


public class NotFoundException extends BaseException {
    private static final Response.Status HTTP_STATUS = Response.Status.NOT_FOUND;

    public NotFoundException(String message) {
        super(message, HTTP_STATUS);
    }
}
