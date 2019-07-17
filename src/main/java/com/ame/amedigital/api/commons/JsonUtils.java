package com.ame.amedigital.api.commons;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtils {

    @Autowired
    @Qualifier("json-mapper")
    private ObjectMapper mapper;

    public String toJson(Object o) {
        try {
            if (o == null) {
                return null;
            }
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            if (json == null) {
                return null;
            }
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean isValidJsonObject(String json, Class<?> clazz) {
        if (json == null || clazz == null) {
            return false;
        }
        try {
            mapper.readValue(json, clazz);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
