package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.CarDto;

public interface CarService extends CrudService<CarDto> {
    Integer getCountCars(String color);
}
