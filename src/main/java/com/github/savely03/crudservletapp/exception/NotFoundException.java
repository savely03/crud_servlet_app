package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {
    public NotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
