package com.github.savely03.crudservletapp.servlet;

import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.service.impl.CarServiceImpl;
import com.github.savely03.crudservletapp.validation.CarDtoValidator;
import jakarta.servlet.annotation.WebServlet;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/v1/car")
public class CarServlet extends BaseServlet<CarDto> {
    public CarServlet() {
        super(CarServiceImpl.getInstance(), CarDtoValidator.getInstance());
    }

    @Override
    public CarDto readObject(BufferedReader reader) throws IOException {
        return objectMapper.readValue(reader, CarDto.class);
    }
}
