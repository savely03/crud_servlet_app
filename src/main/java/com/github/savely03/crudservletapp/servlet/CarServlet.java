package com.github.savely03.crudservletapp.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.exception.CarValidationException;
import com.github.savely03.crudservletapp.service.CarService;
import com.github.savely03.crudservletapp.service.impl.CarServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/api/v1/car")
public class CarServlet extends HttpServlet {
    private CarService carService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        carService = CarServiceImpl.getInstance();
        objectMapper = ObjectMapperConfig.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            objectMapper.writeValue(writer, carService.findAll());
        } catch (CarNotFoundException e) {
            resp.setStatus(e.getResponse().getStatus());
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        try {
            CarDto carDto = objectMapper.readValue(reader, CarDto.class);
            objectMapper.writeValue(writer, carService.save(carDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (CarValidationException e) {
            resp.setStatus(e.getResponse().getStatus());
            writer.write(e.getMessage());
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(e.getMessage());
        } finally {
            reader.close();
            writer.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        try {
            String id = Optional.ofNullable(req.getParameter("id")).orElseThrow(CarValidationException::new);
            CarDto carDto = objectMapper.readValue(reader, CarDto.class);
            objectMapper.writeValue(writer, carService.update(Long.valueOf(id), carDto));
        } catch (CarValidationException | CarNotFoundException e) {
            resp.setStatus(e.getResponse().getStatus());
            writer.write(e.getMessage());
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(e.getMessage());
        } finally {
            reader.close();
            writer.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            String id = Optional.ofNullable(req.getParameter("id")).orElseThrow(CarValidationException::new);
            carService.deleteById(Long.valueOf(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (CarNotFoundException e) {
            resp.setStatus(e.getResponse().getStatus());
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }
}
