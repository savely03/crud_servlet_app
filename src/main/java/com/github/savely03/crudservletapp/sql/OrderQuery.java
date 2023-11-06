package com.github.savely03.crudservletapp.sql;

public final class OrderQuery {

    private OrderQuery() {
    }

    public static final String FIND_ALL = """
            SELECT o.id as order_id, o.client_id as client_id, o.car_id as car_id, o.order_date,
                   c1.model, c1.color, c1.engine_capacity, c1.release_date, c1.price,
                   c2.full_name, c2.date_birthday, c2.gender
            FROM orders o
            JOIN cars c1 ON c1.id = o.car_id
            JOIN clients c2 on c2.id = o.client_id
            """;

    public static final String FIND_BY_ID = FIND_ALL + " WHERE o.id = ?";
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
