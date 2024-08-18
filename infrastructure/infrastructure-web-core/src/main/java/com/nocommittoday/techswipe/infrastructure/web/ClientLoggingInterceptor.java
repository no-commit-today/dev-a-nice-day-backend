package com.nocommittoday.techswipe.infrastructure.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ClientLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ClientLoggingInterceptor.class);

    private final Class<?> clazz;

    public ClientLoggingInterceptor(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ClientLoggingInterceptor(Object object) {
        this.clazz = object.getClass();
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution
    ) throws IOException {
        long startTime = System.currentTimeMillis();

        ClientHttpResponse response = execution.execute(request, body);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;


        log.info("[{}] {} ms : {} {} >> {}",
                clazz.getSimpleName(), duration, request.getMethod(), request.getURI(), response.getStatusCode());

        return response;
    }
}
