package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class CarValidationException extends WebApplicationException {
    public CarValidationException() {
        super("Ошибка валидации автомобиля (проверьте id и поля)", Response.Status.BAD_REQUEST);
    }
}
