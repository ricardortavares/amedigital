package com.ame.amedigital.api.commons;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfiguration {

    /**
     * Default serializer/deserializer for date/time
     *
     * @return ObjectMapper
     */
    @Bean(name = "json-mapper")
    @Primary
    public ObjectMapper mapper() {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        JavaTimeModule module = new JavaTimeModule();

        createSerializerLocalDate(module);
        createSerializerLocalDateTime(module);
        createSerializerOffsetDateTime(module);
        createSerializerZonedDateTime(module);
        createSerializerYearMonth(module);
        createSerializarLocalTime(module);

        builder.modules(module);
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper mapper = builder.build();
        
        return mapper;
    }

    private void createSerializarLocalTime(JavaTimeModule module) {
        JsonSerializer<LocalTime> serializer = new JsonSerializer<LocalTime>() {
            @Override
            public void serialize(LocalTime value, JsonGenerator generator, SerializerProvider serializers)
                    throws IOException, JsonProcessingException {
                generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
            }
        };

        JsonDeserializer<LocalTime> deserializer = new JsonDeserializer<LocalTime>() {
            @Override
            public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException,
            JsonProcessingException {
            	String dateText = parser.getValueAsString().trim();
            	return (StringUtils.isEmpty(dateText) ? null : LocalTime.parse(dateText));  
            }
        };

        module.addSerializer(LocalTime.class, serializer);
        module.addDeserializer(LocalTime.class, deserializer);

    }

    private void createSerializerLocalDate(JavaTimeModule module) {
        JsonSerializer<LocalDate> serializer = new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider provider)
                    throws IOException,
                    JsonProcessingException {
                generator.writeString(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
            }
        };

        JsonDeserializer<LocalDate> deserializer = new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException,
            JsonProcessingException {
            	String dateText = parser.getValueAsString().trim();
            	return (StringUtils.isEmpty(dateText) ? null : LocalDate.parse(dateText));  
            }
        };

        module.addSerializer(LocalDate.class, serializer);
        module.addDeserializer(LocalDate.class, deserializer);
    }

    private void createSerializerLocalDateTime(JavaTimeModule module) {
        JsonSerializer<LocalDateTime> serializer = new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime date, JsonGenerator generator, SerializerProvider provider)
                    throws IOException,
                    JsonProcessingException {
                generator.writeString(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        };

        JsonDeserializer<LocalDateTime> deserializer = new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException,
            JsonProcessingException {
            	String dateText = parser.getValueAsString().trim();
            	return (StringUtils.isEmpty(dateText) ? null : LocalDateTime.parse(dateText));  
            }
        };

        module.addSerializer(LocalDateTime.class, serializer);
        module.addDeserializer(LocalDateTime.class, deserializer);
    }

    private void createSerializerOffsetDateTime(JavaTimeModule module) {
        JsonSerializer<OffsetDateTime> serializer = new JsonSerializer<OffsetDateTime>() {
            @Override
            public void serialize(OffsetDateTime date, JsonGenerator generator, SerializerProvider provider)
                    throws IOException,
                    JsonProcessingException {
                generator.writeString(date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            }
        };

        JsonDeserializer<OffsetDateTime> deserializer = new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException,
            JsonProcessingException {
            	String dateText = parser.getValueAsString().trim();
            	return (StringUtils.isEmpty(dateText) ? null : OffsetDateTime.parse(dateText));  
            }
        };

        module.addSerializer(OffsetDateTime.class, serializer);
        module.addDeserializer(OffsetDateTime.class, deserializer);
    }

    private void createSerializerZonedDateTime(JavaTimeModule module) {
        JsonSerializer<ZonedDateTime> serializer = new JsonSerializer<ZonedDateTime>() {
            @Override
            public void serialize(ZonedDateTime date, JsonGenerator generator, SerializerProvider provider)
                    throws IOException,
                    JsonProcessingException {
                generator.writeString(date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            }
        };

        JsonDeserializer<ZonedDateTime> deserializer = new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException,
            JsonProcessingException {
            	String dateText = parser.getValueAsString().trim();
            	return (StringUtils.isEmpty(dateText) ? null : ZonedDateTime.parse(dateText));  
            }
        };

        module.addSerializer(ZonedDateTime.class, serializer);
        module.addDeserializer(ZonedDateTime.class, deserializer);
    }

    private void createSerializerYearMonth(JavaTimeModule module) {
        JsonSerializer<YearMonth> serializer = new JsonSerializer<YearMonth>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

            @Override
            public void serialize(YearMonth date, JsonGenerator generator, SerializerProvider provider)
                    throws IOException,
                    JsonProcessingException {
                generator.writeString(date.format(formatter));
            }
        };

        JsonDeserializer<YearMonth> deserializer = new JsonDeserializer<YearMonth>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

            @Override
            public YearMonth deserialize(JsonParser parser, DeserializationContext context) throws IOException,
            JsonProcessingException {
                return YearMonth.parse(parser.getValueAsString(), formatter);
            }
        };

        module.addSerializer(YearMonth.class, serializer);
        module.addDeserializer(YearMonth.class, deserializer);
    }
}