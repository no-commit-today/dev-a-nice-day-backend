package com.nocommittoday.client.openai.domain;

public enum MessageRole implements OpenAiCode {

    SYSTEM("system"),
    ASSISTANT("assistant"),
    USER("user"),
    ;

    private final String code;

    MessageRole(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
