package com.nocommittoday.client.openai.http;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nocommittoday.client.openai.exception.OpenAiCodeDeserializeFailureException;
import com.nocommittoday.client.openai.model.OpenAiCode;

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
                .orElseThrow(() -> new OpenAiCodeDeserializeFailureException(targetEnumClass, code));
    }
}
