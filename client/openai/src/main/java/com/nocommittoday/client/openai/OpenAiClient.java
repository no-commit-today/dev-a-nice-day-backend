package com.nocommittoday.client.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nocommittoday.client.openai.model.ChatCompletion;
import com.nocommittoday.client.openai.model.ChatCompletionCreate;
import com.nocommittoday.client.openai.model.ChatModel;
import com.nocommittoday.client.openai.model.ChatObject;
import com.nocommittoday.client.openai.model.FinishReason;
import com.nocommittoday.client.openai.model.MessageRole;
import com.nocommittoday.client.openai.model.OpenAiCode;
import com.nocommittoday.client.openai.http.OpenAiCodeDeserializer;
import com.nocommittoday.client.openai.http.OpenAiCodeSerializer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class OpenAiClient {

    private final RestTemplate restTemplate;

    public OpenAiClient(
            final RestTemplateBuilder restTemplateBuilder,
            final String baseUrl, final String apiKey, final int timeoutMillis
    ) {
        final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(OpenAiCode.class, new OpenAiCodeSerializer());
        simpleModule.addDeserializer(ChatObject.class, new OpenAiCodeDeserializer<>(ChatObject.class));
        simpleModule.addDeserializer(ChatModel.class, new OpenAiCodeDeserializer<>(ChatModel.class));
        simpleModule.addDeserializer(FinishReason.class, new OpenAiCodeDeserializer<>(FinishReason.class));
        simpleModule.addDeserializer(MessageRole.class, new OpenAiCodeDeserializer<>(MessageRole.class));
        objectMapper.registerModule(simpleModule);
        this.restTemplate = restTemplateBuilder
                .rootUri(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .messageConverters(mappingJackson2HttpMessageConverter)
                .setConnectTimeout(Duration.ofMillis(timeoutMillis))
                .build();
    }

    public ChatCompletion createChatCompletion(final ChatCompletionCreate request) {
        return restTemplate.postForObject("/v1/chat/completions", request, ChatCompletion.class);
    }

}
