package com.github.savely03.crudservletapp.util;

import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public final class HikariConnectionManager {
    private static DataSource dataSource;

    static {
        initDatasource();
    }

    private HikariConnectionManager() {
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
