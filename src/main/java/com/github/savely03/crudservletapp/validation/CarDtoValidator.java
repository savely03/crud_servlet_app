package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.exception.CarValidationException;

public class CarDtoValidator extends Validator<CarDto> {
    private static final CarDtoValidator INSTANCE = new CarDtoValidator();

    private CarDtoValidator() {
    }

    public static CarDtoValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public void validate(CarDto carDto) {
        if (carDto.getModel() == null ||
            carDto.getPrice() == null ||
            carDto.getColor() == null ||
            carDto.getEngineCapacity() == null ||
            carDto.getReleaseDate() == null) {
            throw new CarValidationException();
        }
    }

    @Override
    public void validateId(Long id) {
        if (id <= 0) {
            throw new CarValidationException();
        }
    }
}
