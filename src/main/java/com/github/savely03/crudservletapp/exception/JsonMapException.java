package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class JsonMapException extends WebApplicationException {
    private static final String MESSAGE = "Ошибка при конвертации объекта";

    public JsonMapException() {
        super(MESSAGE, Response.Status.BAD_REQUEST);
    }
}
