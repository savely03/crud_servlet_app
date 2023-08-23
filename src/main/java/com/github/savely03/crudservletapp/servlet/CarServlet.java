package com.github.savely03.crudservletapp.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.service.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/car")
public class CarServlet extends HttpServlet {

    private CarService carService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        carService = CarService.getInstance();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()){
            CarDto carDto = objectMapper.readValue(reader, CarDto.class);
            System.out.println(carDto);
        }
    }
}
