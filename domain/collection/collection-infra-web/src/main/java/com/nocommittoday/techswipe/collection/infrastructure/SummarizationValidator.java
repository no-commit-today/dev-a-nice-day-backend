package com.nocommittoday.techswipe.collection.infrastructure;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SummarizationValidator {

    private static final Pattern SUMMARIZATION_RESULT_FORMAT_PATTERN = Pattern.compile("""
            ^1\\. [^\\n]*[가-힣][^\\n]*
            2\\. [^\\n]*[가-힣][^\\n]*
            3\\. [^\\n]*[가-힣][^\\n]*$""");

    public boolean check(final String responseContent) {
        return SUMMARIZATION_RESULT_FORMAT_PATTERN.matcher(responseContent).matches();
    }
}
