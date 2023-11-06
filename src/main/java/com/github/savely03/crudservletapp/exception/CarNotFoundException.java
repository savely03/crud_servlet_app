package com.github.savely03.crudservletapp.exception;

public class CarNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Автомобиль с id - %d не найден";

    public CarNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
