package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.dto.CarDto;

public class CarDtoValidator extends Validator<CarDto> {

    private static final CarDtoValidator INSTANCE = new CarDtoValidator();

    private CarDtoValidator() {
    }

    public static CarDtoValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public void validate(CarDto carDto) {
    }

    @Override
    public void validateId(Long id) {

    }
}
