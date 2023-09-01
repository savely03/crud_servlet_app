package com.github.savely03.crudservletapp.util;

import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public final class ConnectionPool {
    private static DataSource dataSource;

    static {
        initDatasource();
    }

    private ConnectionPool() {
    }

    @SneakyThrows
    public static Connection getConnection() {
        return dataSource.getConnection();
    }

    @SneakyThrows
    private static void initDatasource() {
        Context context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:comp/env/jdbc/hikari");
    }
}
