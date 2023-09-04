package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.util.ConnectionPool;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;
import java.util.function.Supplier;

// Идентификатор у всех реализаций - Long
public interface CrudService<E> {
    List<E> findAll();

    E findById(Long id);

    E save(E obj);

    E update(Long id, E obj);

    void deleteById(Long id);

    boolean exists(Long id);

    @SneakyThrows
    default E wrapInTransaction(Supplier<E> supplier, Connection connection) {
        E result = null;

        try {
            result = supplier.get();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }

        return result;
    }
}
