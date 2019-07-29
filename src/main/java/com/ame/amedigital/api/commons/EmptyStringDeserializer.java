package com.ame.amedigital.api.commons;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class EmptyStringDeserializer extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public EmptyStringDeserializer() {
        addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {
            private static final long serialVersionUID = 1L;

            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext ctx)
                    throws IOException, JsonProcessingException {
                String value = jsonParser.getValueAsString();
                return StringUtils.trimToNull(value);
            }
        });
    }
}
