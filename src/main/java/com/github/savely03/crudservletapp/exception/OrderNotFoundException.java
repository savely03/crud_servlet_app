package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class OrderNotFoundException extends WebApplicationException {
    public OrderNotFoundException(Long id) {
        super(String.format("Заказ с id - %d не найден", id), Response.Status.NOT_FOUND);
    }
}
