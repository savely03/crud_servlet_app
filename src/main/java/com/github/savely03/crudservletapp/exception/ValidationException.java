package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ValidationException extends WebApplicationException {
    public ValidationException() {
        super("Ошибка валидации (проверьте правильность id и полей)", Response.Status.BAD_REQUEST);
    }
}
