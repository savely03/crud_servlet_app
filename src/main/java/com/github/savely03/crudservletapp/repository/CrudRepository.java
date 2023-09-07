package com.github.savely03.crudservletapp.repository;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.savely03.crudservletapp.sql.CrudQuery.*;

// Идентификатор у всех реализаций - Long
public interface CrudRepository<E> {
    @SneakyThrows
    default List<E> findAll(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(FIND_ALL, getTableName()))
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<E> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildEntity(resultSet));
            }

            return result;
        }
    }

    @SneakyThrows
    default Optional<E> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(FIND_BY_ID, getTableName()))
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            E obj = null;

            if (resultSet.next()) {
                obj = buildEntity(resultSet);
            }

            return Optional.ofNullable(obj);
        }
    }

    @SneakyThrows
    default boolean deleteById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(DELETE_BY_ID, getTableName()))
        ) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    default boolean exists(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(EXISTS, getTableName()))
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            long count = 0;

            if (resultSet.next()) {
                count = resultSet.getLong("cnt");
            }

            return count > 0;
        }
    }

    E saveOrUpdate(E obj, Connection connection);

    String getTableName();

    E buildEntity(ResultSet resultSet);
}
