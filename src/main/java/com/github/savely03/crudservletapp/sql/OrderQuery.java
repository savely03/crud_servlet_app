package com.github.savely03.crudservletapp.sql;

public final class OrderQuery {

    private OrderQuery() {
    }

    public static final String INSERT = """
            INSERT INTO orders (client_id, car_id, order_date)
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE = """
            UPDATE orders
            SET client_id = ?,
                car_id = ?,
                order_date = ?
            WHERE id = ?
            """;
}
