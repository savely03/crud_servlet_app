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
}
