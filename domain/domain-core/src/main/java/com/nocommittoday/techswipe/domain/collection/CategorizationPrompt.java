package com.nocommittoday.techswipe.domain.collection;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CategorizationPrompt {

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

    private final String user;

    private CategorizationPrompt(String user) {
        this.user = user;
    }

    public static CategorizationPrompt of(CollectedContent collectedContent) {
        if (!collectedContent.getStatus().categorizable()) {
            throw new IllegalArgumentException("분류 가능한 상태가 아닙니다. status=" + collectedContent.getStatus());
        }
        return new CategorizationPrompt(
                collectedContent.getTitle() + "\n\n" + collectedContent.getContent()
        );
    }

    public String getUser() {
        return user;
    }

    public String getSystem() {
        return SYSTEM_PROMPT;
    }
}
