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

    public static final String MONTHS_WITH_MOST_CARS_COUNT = """
            SELECT a.month as month
            FROM (
            SELECT extract(MONTH FROM order_date) as month,
                   dense_rank() OVER(ORDER BY count(1) DESC ) as rnk
            FROM orders
            GROUP BY month) a
            WHERE a.rnk = 1
            """;

    public static final String EXISTS = """
            SELECT count(1) as cnt_order
            FROM orders
            WHERE id = ?
            """;
}
