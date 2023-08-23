package com.github.savely03.crudservletapp.util;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}
