package com.github.savely03.crudservletapp.service.impl;

import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.mapper.CarMapper;
import com.github.savely03.crudservletapp.repository.CarRepository;
import com.github.savely03.crudservletapp.service.CarService;
import org.mapstruct.factory.Mappers;

import java.util.List;


public class CarServiceImpl implements CarService {
    private static final CarServiceImpl INSTANCE = new CarServiceImpl();
    private final CarRepository carRepository = CarRepository.getInstance();
    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    private CarServiceImpl() {
    }

    public static CarServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<CarDto> findAll() {
        return carRepository.findAll().stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    public CarDto findById(Long id) {
        return carRepository.findById(id)
                .map(carMapper::toDto)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @Override
    public CarDto save(CarDto carDto) {
        return carMapper.toDto(carRepository.save(carMapper.toEntity(carDto)));
    }

    @Override
    public CarDto update(Long id, CarDto carDto) {
        carRepository.findById(id).orElseThrow(
                () -> new CarNotFoundException(id)
        );
        carDto.setId(id);
        carRepository.update(carMapper.toEntity(carDto));
        return carDto;
    }

    @Override
    public void deleteById(Long id) {
        if (!carRepository.deleteById(id)) {
            throw new CarNotFoundException(id);
        }
    }
}
