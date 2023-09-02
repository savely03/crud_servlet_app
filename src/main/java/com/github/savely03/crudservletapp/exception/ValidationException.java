package com.github.savely03.crudservletapp.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class ValidationException extends BaseException {

    private static final String MESSAGE = "Ошибка валидации (проверьте правильность id и полей)";
    private static final Response.Status HTTP_STATUS = Response.Status.BAD_REQUEST;

    public ValidationException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
