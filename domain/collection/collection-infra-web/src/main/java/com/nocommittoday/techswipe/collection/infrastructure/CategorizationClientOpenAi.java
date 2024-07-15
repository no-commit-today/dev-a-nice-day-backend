package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
class CategorizationClientOpenAi implements CategorizationClient {

    public static final String PROMPT = String.format("""
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

    private final ChatClient chatClient;

    public CategorizationClientOpenAi(
            final ChatClient.Builder chatClientBuilder, String model
    ) {
        log.info("분류에 사용되는 모델: {}", model);
        this.chatClient = chatClientBuilder
                .defaultOptions(OpenAiChatOptions.builder()
                        .withModel(model)
                        .build())
                .build();
    }

    @Override
    public String categorize(final CollectedContent collectedContent) {
        return chatClient.prompt()
                .advisors(new CategorizationLoggerAdvisor(collectedContent))
                .system(PROMPT)
                .user(collectedContent.getTitle() + "\n\n" + collectedContent.getContent())
                .call()
                .content()
                .trim();
    }
}
