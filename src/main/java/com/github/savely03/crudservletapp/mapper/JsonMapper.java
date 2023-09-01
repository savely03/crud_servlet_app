package com.github.savely03.crudservletapp.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.exception.JsonMapException;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;

@FunctionalInterface
public interface JsonMapper<T> {
    ObjectMapper OBJECT_MAPPER = ObjectMapperConfig.getInstance();

    default String mapToJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonMapException();
        }
    }

    default T mapToObject(String json) {
        try {
            return readObject(json);
        } catch (JsonProcessingException e) {
            throw new JsonMapException();
        }
    }

    T readObject(String json) throws JsonProcessingException;
}
