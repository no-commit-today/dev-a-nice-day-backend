package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.DomainValidationException;

import java.util.Map;
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

    public static Summary create(String content) {
        Summary summary = new Summary(content);
        if (!summary.isValid()) {
            throw new DomainValidationException("요약 형식이 올바르지 않습니다.", Map.of("content", content));
        }

        return summary;
    }

    public boolean isValid() {
        return SUMMARY_PATTERN.matcher(content).matches();
    }

    public String getContent() {
        return content;
    }
}
