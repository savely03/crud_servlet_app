package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.model.Car;
import com.github.savely03.crudservletapp.util.ConnectionManager;
import com.github.savely03.crudservletapp.util.HikariConnectionManager;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.savely03.crudservletapp.sql.CarQuery.*;

public class CarRepository implements CrudRepository<Car, Long> {

    private static final CarRepository INSTANCE = new CarRepository();
    private final ConnectionManager hikariConnection = HikariConnectionManager.getInstance();

    private CarRepository() {
    }

    public static CarRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public List<Car> findAll() {
        try (Connection connection = hikariConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            List<Car> cars = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cars.add(buildCar(resultSet));
            }

            return cars;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Car> findById(Long id) {
        try (Connection connection = hikariConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Car car = null;

            if (resultSet.next()) {
                car = buildCar(resultSet);
            }

            return Optional.ofNullable(car);
        }
    }

    @SneakyThrows
    @Override
    public Car save(Car car) {
        try (Connection connection = hikariConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getColor());
            preparedStatement.setDouble(3, car.getEngineCapacity());
            preparedStatement.setDate(4, Date.valueOf(car.getReleaseDate()));
            preparedStatement.setBigDecimal(5, car.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                car.setId(resultSet.getLong("id"));
            }

            return car;
        }
    }

    @SneakyThrows
    @Override
    public Car update(Car car) {
        try (Connection connection = hikariConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getColor());
            preparedStatement.setDouble(3, car.getEngineCapacity());
            preparedStatement.setDate(4, Date.valueOf(car.getReleaseDate()));
            preparedStatement.setBigDecimal(5, car.getPrice());
            preparedStatement.setLong(6, car.getId());
            preparedStatement.executeUpdate();

            return car;
        }
    }

    @SneakyThrows
    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = hikariConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    public Integer getCountCars(String color) {
        try (Connection connection = hikariConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CNT_CARS_BY_COLOR)) {

            preparedStatement.setString(1, color);
            ResultSet resultSet = preparedStatement.executeQuery();
            int countCars = 0;

            while (resultSet.next()) {
                countCars = resultSet.getInt("count_cars");
            }

            return countCars;
        }
    }

    @SneakyThrows
    private Car buildCar(ResultSet resultSet) {
        return Car.builder()
                .id(resultSet.getLong("id"))
                .model(resultSet.getString("model"))
                .color(resultSet.getString("color"))
                .engineCapacity(resultSet.getDouble("engine_capacity"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .price(resultSet.getBigDecimal("price"))
                .build();
    }
}
