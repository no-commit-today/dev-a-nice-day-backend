package com.devniceday.batch.domain;

import java.util.regex.Pattern;

public record Summary(
        String content
) {

    private static final Pattern SUMMARY_PATTERN = Pattern.compile("""
            ^1\\. [^\\n]*[가-힣][^\\n]*
            2\\. [^\\n]*[가-힣][^\\n]*
            3\\. [^\\n]*[가-힣][^\\n]*$""");

    public boolean isValid() {
        return SUMMARY_PATTERN.matcher(content).matches();
    }

}
