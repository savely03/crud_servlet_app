package com.github.savely03.crudservletapp.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ClientValidationException extends BaseException {
    public ClientValidationException() {
        super(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    public String getMessage() {
        return "Ошибка валидации клиента (проверьте id и поля)";
    }
}
