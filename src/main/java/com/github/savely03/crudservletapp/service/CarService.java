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

    @SneakyThrows
    @Override
    public List<CarDto> findAll() {
        try (Connection connection = ConnectionPool.getConnection()) {
            return carRepository.findAll(connection).stream()
                    .map(carMapper::toDto)
                    .toList();
        }
    }

    @SneakyThrows
    @Override
    public CarDto findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            return carRepository.findById(id, connection)
                    .map(carMapper::toDto)
                    .orElseThrow(() -> new CarNotFoundException(id));
        }
    }

    @SneakyThrows
    @Override
    public CarDto save(CarDto carDto) {
        Connection connection = ConnectionPool.getConnectionWithNoAutoCommit();
        return wrapInTransaction(() ->
                carMapper.toDto(carRepository.saveOrUpdate(carMapper.toEntity(carDto), connection)), connection);
    }

    @SneakyThrows
    @Override
    public CarDto update(Long id, CarDto carDto) {
        try (Connection connection = ConnectionPool.getConnection()) {
            carRepository.findById(id, connection).orElseThrow(
                    () -> new CarNotFoundException(id)
            );
            carDto.setId(id);
            carRepository.saveOrUpdate(carMapper.toEntity(carDto), connection);
            return carDto;
        }
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            if (!carRepository.deleteById(id, connection)) {
                throw new CarNotFoundException(id);
            }
        }
    }

    @SneakyThrows
    @Override
    public boolean exists(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            return carRepository.exists(id, connection);
        }
    }
}
