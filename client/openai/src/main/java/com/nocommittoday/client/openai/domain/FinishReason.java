package com.nocommittoday.client.openai.domain;

public enum FinishReason implements OpenAiCode {

    STOP("stop"),
    LENGTH("length"),
    CONTENT_FILTER("content_filter"),
    TOOL_CALLS("tool_calls");

    private final String code;

    FinishReason(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
