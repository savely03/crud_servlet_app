package com.github.savely03.crudservletapp.exception;

import jakarta.servlet.http.HttpServletResponse;

public class CarNotFoundException extends BaseException {
    private final Long id;
    public CarNotFoundException(Long id) {
        super(HttpServletResponse.SC_NOT_FOUND);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Машина с id - %d не найдена", id);
    }
}
