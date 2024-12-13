package com.devniceday.batch.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

public record CategorizationPrompt(
        String user
) {

    private static final String SYSTEM_PROMPT = String.format("""
                    - 당신은 분류를 정말 잘 하는 봇입니다.
                    - 카테고리는 [%s] 중 선택합니다.
                    - 카테고리는 최소 1개에서 최대 3개입니다.
                    - 아래 [답변 형식]에 맞게 답변해야 합니다.
                    - 지시한 내용들을 지키지 못하면 당신은 불이익을 받을 것입니다.
                    
                    [답변 형식]
                    - ...
                    - ...
                    """,
            Arrays.stream(CollectionCategory.values())
                    .map(CollectionCategory::name)
                    .collect(Collectors.joining(","))
    );

    public static CategorizationPrompt of(String title, String content) {
        return new CategorizationPrompt(
                title + "\n\n" + content
        );
    }

    public String getUser() {
        return user;
    }

    public String getSystem() {
        return SYSTEM_PROMPT;
    }
}
