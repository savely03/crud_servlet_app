package com.github.savely03.crudservletapp.mapper;

import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.model.Car;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper extends GeneralMapper<CarDto, Car> {
}
