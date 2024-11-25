package com.devniceday.batch.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionCategoryTest {

    @Test
    void 생성형_모델의_응답으로_생성할_수_있다() {
        // given
        String chatCompletionResult = "" +
                "- " + CollectionCategory.SERVER.name() + "\n" +
                "- " + CollectionCategory.AI.name();
        // when
        List<CollectionCategory> result = CollectionCategory.createListFromChatCompletionResult(
                chatCompletionResult
        );

        // then
        assertThat(result).containsExactly(
                CollectionCategory.AI,
                CollectionCategory.SERVER
        );
    }

    @Test
    void 생성형_모델의_응답으로_생성할_때_잘못된_카테고리가_포함되면_null을_반환한다() {
        // given
        // when
        // then
        assertThat(CollectionCategory.createListFromChatCompletionResult("invalid")).isNull();
        assertThat(CollectionCategory.createListFromChatCompletionResult("- invalid")).isNull();
        assertThat(CollectionCategory.createListFromChatCompletionResult(
                "- " + CollectionCategory.SERVER.name() + "- " + CollectionCategory.AI.name()
        )).isNull();
    }

    @Test
    void 생성할_때_카테고리_중복은_제거한다() {
        // given
        // when
        var result = CollectionCategory.createListFromChatCompletionResult(
                "- " + CollectionCategory.SERVER.name() + "\n"
                        + "- " + CollectionCategory.AI.name() + "\n"
                        + "- " + CollectionCategory.SERVER.name()
        );

        // then
        assertThat(result).containsOnly(
                CollectionCategory.AI,
                CollectionCategory.SERVER
        );
    }

    @Test
    void 생성할_때_카테고리는_이름순으로_정렬된다() {
        // given
        // when
        var result = CollectionCategory.createListFromChatCompletionResult(
                "- " + CollectionCategory.SERVER.name() + "\n"
                        + "- " + CollectionCategory.AI.name() + "\n"
                        + "- " + CollectionCategory.DATA_ENGINEERING.name()
        );

        // then
        assertThat(result).containsExactly(
                CollectionCategory.AI,
                CollectionCategory.DATA_ENGINEERING,
                CollectionCategory.SERVER
        );
    }

    @Test
    void 카테고리_개수는_1개이상_3개이하_이다() {
        // given
        // when
        // then
        assertThat(CollectionCategory.createListFromChatCompletionResult("")).isNull();
        assertThat(CollectionCategory.createListFromChatCompletionResult(
                "- " + CollectionCategory.SERVER.name() + "\n"
                        + "- " + CollectionCategory.AI.name() + "\n"
                        + "- " + CollectionCategory.DATA_ENGINEERING.name() + "\n"
                        + "- " + CollectionCategory.WEB.name()
        )).isNull();
    }
}