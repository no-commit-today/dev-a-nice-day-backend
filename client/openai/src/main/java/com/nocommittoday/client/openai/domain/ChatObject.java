package com.nocommittoday.client.openai.domain;

public enum ChatObject implements OpenAiCode {

    CHAT_COMPLETION("chat.completion"),
    ;

    private final String code;

    ChatObject(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
