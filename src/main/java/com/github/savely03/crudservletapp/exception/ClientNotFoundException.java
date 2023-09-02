package com.github.savely03.crudservletapp.exception;

public class ClientNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Клиент с id - %d не найден";

    public ClientNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
