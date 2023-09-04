package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.CarDto;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.mapper.CarMapper;
import com.github.savely03.crudservletapp.repository.CarRepository;
import com.github.savely03.crudservletapp.util.ConnectionPool;
import lombok.SneakyThrows;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
import java.util.List;


public class CarService implements CrudService<CarDto> {
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

    @SneakyThrows
    @Override
    public CarDto save(CarDto carDto) {
        Connection connection = ConnectionPool.getConnectionWithNoAutoCommit();
        return wrapInTransaction(() ->
                carMapper.toDto(carRepository.save(carMapper.toEntity(carDto), connection)), connection);
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

    @Override
    public boolean exists(Long id) {
        return carRepository.exists(id);
    }
}
