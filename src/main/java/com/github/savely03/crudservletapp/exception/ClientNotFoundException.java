package com.github.savely03.crudservletapp.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ClientNotFoundException extends BaseException {
    private final Long id;

    public ClientNotFoundException(Long id) {
        super(HttpServletResponse.SC_NOT_FOUND);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Клиент с id - %d не найден", id);
    }
}
