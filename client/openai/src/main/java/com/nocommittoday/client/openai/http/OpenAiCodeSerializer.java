package com.nocommittoday.client.openai.http;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nocommittoday.client.openai.domain.OpenAiCode;

import java.io.IOException;

public class OpenAiCodeSerializer extends JsonSerializer<OpenAiCode> {

    @Override
    public void serialize(final OpenAiCode value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException {
        gen.writeString(value.getCode());
    }
}
