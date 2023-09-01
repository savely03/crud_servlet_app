package com.github.savely03.crudservletapp.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.exception.NotFoundException;
import com.github.savely03.crudservletapp.exception.ValidationException;
import com.github.savely03.crudservletapp.service.CrudService;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import com.github.savely03.crudservletapp.validation.Validator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseServlet<T> extends HttpServlet {
    private final CrudService<T> service;
    private final Validator<T> validator;
    protected final ObjectMapper objectMapper;

    protected BaseServlet(CrudService<T> service, Validator<T> validator) {
        this.service = service;
        this.validator = validator;
        objectMapper = ObjectMapperConfig.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer;
        try {
            writer = resp.getWriter();
            String id = req.getParameter("id");
            if (id != null) {
                validator.validateId(id);
                writeResponse(writer, service.findById(Long.valueOf(id)), resp, HttpServletResponse.SC_OK);
            } else {
                writeResponse(writer, service.findAll(), resp, HttpServletResponse.SC_OK);
            }
        } catch (ValidationException | NotFoundException e) {
            writeResponseStatus(resp, e.getResponse().getStatus());
        } catch (Exception e) {
            writeResponseStatus(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader reader;
        PrintWriter writer;
        try {
            reader = req.getReader();
            writer = resp.getWriter();
            T dto = readObject(reader);
            validator.validate(dto);
            writeResponse(writer, service.save(dto), resp, HttpServletResponse.SC_CREATED);
        } catch (ValidationException | NotFoundException e) {
            writeResponseStatus(resp, e.getResponse().getStatus());
        } catch (JsonProcessingException e) {
            writeResponseStatus(resp, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            writeResponseStatus(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader reader;
        PrintWriter writer;
        try {
            writer = resp.getWriter();
            reader = req.getReader();
            String id = req.getParameter("id");
            T dto = readObject(reader);
            validator.validate(id, dto);
            writeResponse(writer, service.update(Long.valueOf(id), dto), resp, HttpServletResponse.SC_OK);
        } catch (ValidationException | NotFoundException e) {
            writeResponseStatus(resp, e.getResponse().getStatus());
        } catch (JsonProcessingException e) {
            writeResponseStatus(resp, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            writeResponseStatus(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter("id");
            validator.validateId(id);
            service.deleteById(Long.valueOf(id));
            writeResponseStatus(resp, HttpServletResponse.SC_NO_CONTENT);
        } catch (NotFoundException | ValidationException e) {
            writeResponseStatus(resp, e.getResponse().getStatus());
        } catch (Exception e) {
            writeResponseStatus(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    private void writeResponseStatus(HttpServletResponse response, int status) {
        response.setStatus(status);
    }

    private void writeResponse(PrintWriter writer, Object obj, HttpServletResponse response, int status) throws IOException {
        objectMapper.writeValue(writer, obj);
        writeResponseStatus(response, status);
    }


    public abstract T readObject(BufferedReader reader) throws IOException;

}
