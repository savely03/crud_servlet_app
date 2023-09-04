package com.github.savely03.crudservletapp.util;

import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public final class DataSourceConfig {

    private static DataSource dataSource;

    private DataSourceConfig() {
    }

    static {
        initDatasource();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    @SneakyThrows
    private static void initDatasource() {
        Context context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:comp/env/jdbc/hikari");
    }
}
