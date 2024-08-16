package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CategorizationProcessor {

    public static final int MIN_CATEGORY_NUM = 1;
    public static final int MAX_CATEGORY_NUM = 3;

    private static final Pattern RESULT_PATTERN = Pattern.compile("^- ("
            + Arrays.stream(CollectionCategory.values())
            .map(CollectionCategory::name)
            .collect(Collectors.joining("|"))
            + ")$");

    private final CategorizationClient categorizationClient;

    public CategorizationProcessor(CategorizationClient categorizationClient) {
        this.categorizationClient = categorizationClient;
    }

    public CategorizationResult categorize(CollectedContent collectedContent) {
        try {
            String responseContent = categorizationClient.categorize(collectedContent);
            List<String> responseContentLines = Arrays.stream(
                            responseContent.split("\n"))
                    .filter(message -> !message.isBlank())
                    .map(String::trim)
                    .toList();

            if (responseContentLines.stream()
                    .anyMatch(line -> !RESULT_PATTERN.matcher(line).matches())) {
                throw new CategorizationResponseInvalidException(
                        "분류 결과가 올바르지 않습니다. ", collectedContent.getId(), responseContent);
            }

            CollectionCategoryList categoryList = CollectionCategoryList.create(
                    responseContentLines.stream()
                            .map(line -> line.replaceFirst("^- ", ""))
                            .map(CollectionCategory::valueOf)
                            .toList()
            );

            return CategorizationResult.success(categoryList);
        } catch (Exception e) {
            return CategorizationResult.failure(e);
        }
    }
}
