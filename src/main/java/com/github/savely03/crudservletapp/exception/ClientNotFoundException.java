package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ClientNotFoundException extends WebApplicationException {
    public ClientNotFoundException(Long id) {
        super(String.format("Клиент с id - %d не найден", id), Response.Status.NOT_FOUND);
    }
}
