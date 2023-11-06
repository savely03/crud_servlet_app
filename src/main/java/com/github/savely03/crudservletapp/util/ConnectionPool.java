package com.github.savely03.crudservletapp.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;

@UtilityClass
public class ConnectionPool {
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
