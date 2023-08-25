package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class OrderValidationException extends WebApplicationException {

    public OrderValidationException() {
        super("Ошибка валидации заказа (проверьте id и поля)", Response.Status.BAD_REQUEST);
    }
}
