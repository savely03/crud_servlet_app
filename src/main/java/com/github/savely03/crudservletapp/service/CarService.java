package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.mapper.CarMapper;
import com.github.savely03.crudservletapp.repository.CarRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;


public class CarService implements CrudService<CarDto, Long> {
    private static final CarService INSTANCE = new CarService();
    private final CarRepository carRepository = CarRepository.getInstance();
    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    private CarService() {
    }

    public static CarService getInstance() {
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
        carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
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
