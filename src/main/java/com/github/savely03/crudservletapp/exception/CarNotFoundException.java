package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class CarNotFoundException extends WebApplicationException {
    public CarNotFoundException(Long id) {
        super(String.format("Машина с id - %d не найдена", id), Response.Status.NOT_FOUND);
    }
}
