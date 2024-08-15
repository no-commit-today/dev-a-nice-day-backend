package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.content.Summary;

import javax.annotation.Nullable;

public class SummaryBuilder {

    @Nullable
    private String content;

    public SummaryBuilder() {
    }

    public static Summary create() {
        return new SummaryBuilder()
                .content("""
                        1. 요약-1
                        2. 요약-2
                        3. 요약-3
                        """.trim()
                ).build();
    }

    public SummaryBuilder content(String content) {
        this.content = content;
        return this;
    }

    public Summary build() {
        return new Summary(content);
    }
}
