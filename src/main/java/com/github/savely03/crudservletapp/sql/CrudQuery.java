package com.github.savely03.crudservletapp.sql;

public final class CrudQuery {

    private CrudQuery() {
    }

    public static final String FIND_ALL = """
            SELECT *
            FROM %s
            """;

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    public static final String DELETE_BY_ID = """
            DELETE
            FROM %s
            WHERE id = ?
            """;

    public static final String EXISTS = """
            SELECT count(1) as cnt
            FROM %s
            WHERE id = ?
            """;
}
