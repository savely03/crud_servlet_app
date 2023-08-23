package com.github.savely03.crudservletapp.sql;

public final class OrderQuery {

    private OrderQuery() {
    }

    public static final String FIND_ALL = """
            SELECT id,
                   client_id,
                   car_id,
                   order_date
            FROM orders
            """;

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    public static final String DELETE_BY_ID = """
            DELETE
            FROM orders
            WHERE id = ?
            """;

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
