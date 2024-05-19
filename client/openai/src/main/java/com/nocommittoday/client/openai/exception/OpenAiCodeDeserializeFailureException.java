package com.nocommittoday.client.openai.exception;

import com.nocommittoday.client.openai.model.OpenAiCode;

public class OpenAiCodeDeserializeFailureException extends RuntimeException {

    public OpenAiCodeDeserializeFailureException(
            final Class<? extends OpenAiCode> openAiCodeClass, final String code
    ) {
        super(String.format(
                "OpenAiCode=[%s], code=[%s] 가 존재하지 않음", openAiCodeClass.getName(), code)
        );
    }

}
