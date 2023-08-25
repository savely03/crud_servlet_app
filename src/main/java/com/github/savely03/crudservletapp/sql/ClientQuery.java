package com.github.savely03.crudservletapp.sql;

public final class ClientQuery {

    private ClientQuery() {
    }

    public static final String FIND_ALL = """
            SELECT id,
                   full_name,
                   date_birthday,
                   gender
            FROM clients
            """;

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    public static final String DELETE_BY_ID = """
            DELETE
            FROM clients
            WHERE id = ?
            """;

    public static final String INSERT = """
            INSERT INTO clients (full_name, date_birthday, gender)
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE = """
            UPDATE clients
            SET full_name = ?,
                date_birthday = ?,
                gender = ?
            WHERE id = ?
            """;

    public static final String CNT_CARS_GROUP_BY_CLIENT = """
            SELECT c.id as id,
                   c.full_name as full_name,
                   count(1) as cnt
            FROM clients c
            JOIN orders o ON c.id = o.client_id
            GROUP BY c.id, c.full_name
            """;

    public static final String FULL_NAMES_CLIENTS_WITH_MAX_ORDERS_CNT = """
            SELECT a.full_name as full_name
            FROM (
            SELECT c.full_name as full_name,
                   dense_rank() OVER (ORDER BY count(1) DESC ) as rnk
            FROM clients c
            JOIN orders o ON c.id = o.client_id
            GROUP BY c.id ) a
            WHERE a.rnk = 1
            """;
}
