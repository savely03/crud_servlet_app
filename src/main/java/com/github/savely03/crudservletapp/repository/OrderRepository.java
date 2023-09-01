package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.model.Order;
import com.github.savely03.crudservletapp.util.ConnectionPool;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.savely03.crudservletapp.sql.OrderQuery.*;

public class OrderRepository implements CrudRepository<Order> {

    public static final OrderRepository INSTANCE = new OrderRepository();

    private OrderRepository() {
    }

    public static OrderRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public List<Order> findAll() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            List<Order> orders = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }

            return orders;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Order> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Order order = null;

            if (resultSet.next()) {
                order = buildOrder(resultSet);
            }

            return Optional.ofNullable(order);
        }
    }

    @SneakyThrows
    @Override
    public Order save(Order order) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

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

    @SneakyThrows
    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public boolean exists(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXISTS)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            long count = 0;

            if (resultSet.next()) {
                count = resultSet.getLong("cnt_order");
            }

            return count > 0;
        }
    }

    @SneakyThrows
    public List<Integer> findMonthsWithMostOrdersCars() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(MONTHS_WITH_MOST_CARS_COUNT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> months = new ArrayList<>();

            while (resultSet.next()) {
                months.add(resultSet.getInt("month"));
            }

            return months;
        }
    }

    @SneakyThrows
    private Order buildOrder(ResultSet resultSet) {
        return Order.builder()
                .id(resultSet.getLong("id"))
                .clientId(resultSet.getLong("client_id"))
                .carId(resultSet.getLong("car_id"))
                .orderDate(resultSet.getDate("order_date").toLocalDate())
                .build();
    }
}
