package com.nocommittoday.client.openai.http;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nocommittoday.client.openai.domain.OpenAiCode;

import java.io.IOException;
import java.util.EnumSet;

public class OpenAiCodeDeserializer<E extends Enum<E> & OpenAiCode> extends JsonDeserializer<E> {

    private final Class<E> targetEnumClass;

    public OpenAiCodeDeserializer(final Class<E> targetEnumClass) {
        this.targetEnumClass = targetEnumClass;
    }

    @Override
    public E deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JacksonException {
        final String code = p.getText();
        return EnumSet.allOf(targetEnumClass).stream()
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "OpenAiCode=[%s], code=[%s] 가 존재하지 않음", targetEnumClass.getSimpleName(), code))
                );
    }
}
