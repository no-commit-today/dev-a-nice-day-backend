package com.nocommittoday.techswipe.domain.content;

import java.util.regex.Pattern;

public class Summary {

    private static final Pattern SUMMARY_PATTERN = Pattern.compile("""
            ^1\\. [^\\n]*[가-힣][^\\n]*
            2\\. [^\\n]*[가-힣][^\\n]*
            3\\. [^\\n]*[가-힣][^\\n]*$""");

    private final String content;

    public Summary(String content) {
        this.content = content;
    }

    public boolean isValid() {
        return SUMMARY_PATTERN.matcher(content).matches();
    }

    public String getContent() {
        return content;
    }
}
