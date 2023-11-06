package com.github.savely03.crudservletapp.exception;

import com.github.savely03.crudservletapp.dto.ResponseErrorDto;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;


@Getter
public class BaseException extends WebApplicationException {

    private final ResponseErrorDto responseError = new ResponseErrorDto();

    public BaseException(String message, Response.Status status) {
        super(message, status);
        responseError.setMessage(message);
        responseError.setStatus(status.getStatusCode());
    }
}
