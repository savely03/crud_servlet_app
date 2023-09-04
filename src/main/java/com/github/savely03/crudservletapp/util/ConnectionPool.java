package com.github.savely03.crudservletapp.util;

import lombok.SneakyThrows;

import java.sql.Connection;

public final class ConnectionPool {
    private ConnectionPool() {
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DataSourceConfig.getDataSource().getConnection();
    }

    @SneakyThrows
    public static Connection getConnectionWithNoAutoCommit() {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    @SneakyThrows
    public static void putConnection(Connection connection) {
        if (connection != null) {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
