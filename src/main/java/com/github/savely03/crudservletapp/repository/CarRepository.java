package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.model.Car;
import lombok.SneakyThrows;

import java.sql.*;

import static com.github.savely03.crudservletapp.sql.CarQuery.INSERT;
import static com.github.savely03.crudservletapp.sql.CarQuery.UPDATE;

public class CarRepository implements CrudRepository<Car> {
    private static final String TABLE_NAME = "cars";
    private static final CarRepository INSTANCE = new CarRepository();

    private CarRepository() {
    }

    public static CarRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public Car saveOrUpdate(Car car, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            if (car.getId() == null || !exists(car.getId(), connection)) {
                preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(UPDATE);
                preparedStatement.setLong(6, car.getId());
            }

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
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @SneakyThrows
    @Override
    public Car buildEntity(ResultSet resultSet) {
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
