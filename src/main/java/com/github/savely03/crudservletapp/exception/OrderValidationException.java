package com.github.savely03.crudservletapp.exception;

import jakarta.servlet.http.HttpServletResponse;

public class OrderValidationException extends BaseException {

    protected OrderValidationException() {
        super(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    public String getMessage() {
        return "Ошибка валидации заказа (проверьте id и поля)";
    }
}
