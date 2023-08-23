package com.github.savely03.crudservletapp.exception;

public abstract class BaseException extends RuntimeException {
    private final int httStatus;

    protected BaseException(int httpStatus) {
        this.httStatus = httpStatus;
    }

    public int getHttStatus() {
        return httStatus;
    }
}
