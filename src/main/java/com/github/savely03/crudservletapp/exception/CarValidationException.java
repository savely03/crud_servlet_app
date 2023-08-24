package com.github.savely03.crudservletapp.exception;

import jakarta.servlet.http.HttpServletResponse;

public class CarValidationException extends BaseException{
    protected CarValidationException() {
        super(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    public String getMessage() {
        return "Ошибка валидации автомобиля (проверьте id и поля)";
    }
}
