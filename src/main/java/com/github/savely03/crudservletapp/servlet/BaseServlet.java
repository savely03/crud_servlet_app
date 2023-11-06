package com.github.savely03.crudservletapp.servlet;

import com.github.savely03.crudservletapp.exception.JsonMapException;
import com.github.savely03.crudservletapp.exception.NotFoundException;
import com.github.savely03.crudservletapp.exception.ValidationException;
import com.github.savely03.crudservletapp.mapper.JsonMapper;
import com.github.savely03.crudservletapp.service.CrudService;
import com.github.savely03.crudservletapp.validation.Validator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public abstract class BaseServlet<T> extends HttpServlet {
    private final CrudService<T> service;
    private final Validator<T> validator;
    private final JsonMapper<T> jsonMapper;

    protected BaseServlet(CrudService<T> service, Validator<T> validator, JsonMapper<T> jsonMapper) {
        this.service = service;
        this.validator = validator;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter("id");
            if (id != null) {
                validator.validateId(id);
                writeResponse(service.findById(Long.valueOf(id)), resp, HttpServletResponse.SC_OK);
            } else {
                writeResponse(service.findAll(), resp, HttpServletResponse.SC_OK);
            }
        } catch (ValidationException | NotFoundException e) {
            writeResponse(e.getResponseError(), resp, e.getResponse().getStatus());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            T dto = jsonMapper.mapToObject(readRequestBody(req));
            validator.validate(dto);
            writeResponse(service.save(dto), resp, HttpServletResponse.SC_CREATED);
        } catch (ValidationException | NotFoundException | JsonMapException e) {
            writeResponse(e.getResponseError(), resp, e.getResponse().getStatus());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter("id");
            T dto = jsonMapper.mapToObject(readRequestBody(req));
            validator.validate(id, dto);
            writeResponse(service.update(Long.valueOf(id), dto), resp, HttpServletResponse.SC_OK);
        } catch (ValidationException | NotFoundException | JsonMapException e) {
            writeResponse(e.getResponseError(), resp, e.getResponse().getStatus());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter("id");
            validator.validateId(id);
            service.deleteById(Long.valueOf(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NotFoundException | ValidationException e) {
            writeResponse(e.getResponseError(), resp, e.getResponse().getStatus());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void writeResponse(Object obj, HttpServletResponse resp, int status) {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(jsonMapper.mapToJson(obj));
            resp.setStatus(status);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String readRequestBody(HttpServletRequest req) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
