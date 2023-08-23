package com.github.savely03.crudservletapp.validation;

public abstract class Validator<T> {
    public void validate(Long id, T obj) {
        validateId(id);
        validate(obj);
    }

    public abstract void validate(T obj);

    public abstract void validateId(Long id);
}
