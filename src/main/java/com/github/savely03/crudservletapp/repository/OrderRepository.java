package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.model.Car;
import com.github.savely03.crudservletapp.model.Client;
import com.github.savely03.crudservletapp.model.Order;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.savely03.crudservletapp.sql.OrderQuery.*;

public class OrderRepository implements CrudRepository<Order> {
    private static final String TABLE_NAME = "orders";

    private static final OrderRepository INSTANCE = new OrderRepository();

    private OrderRepository() {
    }

    public static OrderRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public List<Order> findAll(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildEntity(resultSet));
            }

            return result;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Order> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Order order = null;

            if (resultSet.next()) {
                order = buildEntity(resultSet);
            }

            return Optional.ofNullable(order);
        }
    }

    @SneakyThrows
    public Order saveOrUpdate(Order order, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            if (order.getId() == null || !exists(order.getId(), connection)) {
                preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(UPDATE);
                preparedStatement.setLong(4, order.getId());
            }

            preparedStatement.setLong(1, order.getClient().getId());
            preparedStatement.setLong(2, order.getCar().getId());
            preparedStatement.setDate(3, Date.valueOf(order.getOrderDate()));

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
            }
            return order;
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
    public Order buildEntity(ResultSet resultSet) {
        Client client = Client.builder()
                .id(resultSet.getLong("client_id"))
                .fullName(resultSet.getString("full_name"))
                .dateBirthday(resultSet.getDate("date_birthday").toLocalDate())
                .gender(resultSet.getShort("gender"))
                .build();
        Car car = Car.builder()
                .id(resultSet.getLong("car_id"))
                .model(resultSet.getString("model"))
                .engineCapacity(resultSet.getDouble("engine_capacity"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .price(resultSet.getBigDecimal("price"))
                .color(resultSet.getString("color"))
                .build();
        return Order.builder()
                .id(resultSet.getLong("order_id"))
                .client(client)
                .car(car)
                .orderDate(resultSet.getDate("order_date").toLocalDate())
                .build();
    }
}
