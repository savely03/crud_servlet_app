package com.github.savely03.crudservletapp.sql;

public final class CarQuery {

    private CarQuery() {
    }

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
}
