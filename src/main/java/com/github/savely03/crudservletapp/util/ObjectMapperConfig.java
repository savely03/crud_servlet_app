package com.github.savely03.crudservletapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperConfig {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    static {
        INSTANCE.registerModule(new JavaTimeModule());
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }
}
