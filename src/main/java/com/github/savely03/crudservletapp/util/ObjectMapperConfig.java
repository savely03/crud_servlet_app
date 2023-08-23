package com.github.savely03.crudservletapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperConfig {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private ObjectMapperConfig() {
    }

    static {
        INSTANCE.registerModule(new JavaTimeModule());
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }
}
