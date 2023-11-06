package com.github.savely03.crudservletapp.exception;

public class OrderNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Заказ с id - %d не найден";

    public OrderNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
