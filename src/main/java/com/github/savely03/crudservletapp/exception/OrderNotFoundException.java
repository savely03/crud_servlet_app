package com.github.savely03.crudservletapp.exception;

import jakarta.servlet.http.HttpServletResponse;

public class OrderNotFoundException extends BaseException {
    private final Long id;

    public OrderNotFoundException(Long id) {
        super(HttpServletResponse.SC_NOT_FOUND);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Заказ с id - %d не найден", id);
    }
}
