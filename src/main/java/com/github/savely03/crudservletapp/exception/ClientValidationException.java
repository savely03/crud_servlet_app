package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ClientValidationException extends WebApplicationException {
    public ClientValidationException() {
        super("Ошибка валидации клиента (проверьте id и поля)", Response.Status.BAD_REQUEST);
    }
}
