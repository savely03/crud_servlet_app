package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;

public abstract class Validator<T> {
    public void validate(String id, T obj) {
        validateId(id);
        validate(obj);
    }

    public abstract void validate(T obj);

    public void validateId(Long id) {
        if (id < 0) {
            throw new ValidationException();
        }
    }

    public void validateId(String id) {
        if (!StringUtils.isNumeric(id)) {
            throw new ValidationException();
        }
        validateId(Long.valueOf(id));
    }
}
