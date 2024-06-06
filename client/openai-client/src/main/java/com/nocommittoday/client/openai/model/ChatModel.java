package com.nocommittoday.client.openai.model;

public enum ChatModel implements OpenAiCode {

    GPT_3_5_TURBO_0125("gpt-3.5-turbo-0125"),
    ;

    private final String code;

    ChatModel(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
