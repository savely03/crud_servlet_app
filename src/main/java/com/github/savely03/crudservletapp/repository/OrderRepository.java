package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.model.Order;
import com.github.savely03.crudservletapp.util.ConnectionPool;
import lombok.SneakyThrows;

import java.sql.*;

import static com.github.savely03.crudservletapp.sql.OrderQuery.INSERT;
import static com.github.savely03.crudservletapp.sql.OrderQuery.UPDATE;

public class OrderRepository implements CrudRepository<Order> {
    private static final String TABLE_NAME = "orders";

    public static final OrderRepository INSTANCE = new OrderRepository();

    private OrderRepository() {
    }

    public static OrderRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public Order save(Order order, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, order.getClientId());
            preparedStatement.setLong(2, order.getCarId());
            preparedStatement.setDate(3, Date.valueOf(order.getOrderDate()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
            }

            return order;
        }
    }

    @SneakyThrows
    @Override
    public Order update(Order order) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setLong(1, order.getClientId());
            preparedStatement.setLong(2, order.getCarId());
            preparedStatement.setDate(3, Date.valueOf(order.getOrderDate()));
            preparedStatement.setLong(4, order.getId());
            preparedStatement.executeUpdate();

            return order;
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @SneakyThrows
    @Override
    public Order buildEntity(ResultSet resultSet) {
        return Order.builder()
                .id(resultSet.getLong("id"))
                .clientId(resultSet.getLong("client_id"))
                .carId(resultSet.getLong("car_id"))
                .orderDate(resultSet.getDate("order_date").toLocalDate())
                .build();
    }
}
