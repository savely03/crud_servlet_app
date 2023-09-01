package com.github.savely03.crudservletapp.sql;

public final class CarQuery {

    private CarQuery() {
    }

    public static final String FIND_ALL = """
            SELECT id,
                   model,
                   color,
                   engine_capacity,
                   release_date,
                   price
            FROM cars
            """;

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    public static final String DELETE_BY_ID = """
            DELETE
            FROM cars
            WHERE id = ?
            """;

    public static final String INSERT = """
            INSERT INTO cars (model, color, engine_capacity, release_date, price)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String UPDATE = """
            UPDATE cars
            SET model = ?,
                color = ?,
                engine_capacity = ?,
                release_date = ?,
                price = ?
            WHERE id = ?
            """;

    public static final String CNT_CARS_BY_COLOR = """
            SELECT count(1) as count_cars
            FROM cars c
            JOIN orders o ON c.id = o.car_id
            WHERE lower(c.color) = ?
            """;

    public static final String EXISTS = """
            SELECT count(1) as cnt_car
            FROM cars
            WHERE id = ?
            """;
}
