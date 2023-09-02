package com.github.savely03.crudservletapp.servlet;

import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.service.CarService;
import com.github.savely03.crudservletapp.validation.CarDtoValidator;
import jakarta.servlet.annotation.WebServlet;

import static com.github.savely03.crudservletapp.mapper.JsonMapper.OBJECT_MAPPER;

@WebServlet("/api/v1/car")
public class CarServlet extends BaseServlet<CarDto> {
    public CarServlet() {
        super(CarService.getInstance(), CarDtoValidator.getInstance(), s -> OBJECT_MAPPER.readValue(s, CarDto.class));
    }
}
