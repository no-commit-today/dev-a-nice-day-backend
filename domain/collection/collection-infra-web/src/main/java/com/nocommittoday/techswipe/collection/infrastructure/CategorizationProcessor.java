package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CategorizationProcessor {

    private static final Logger log = LoggerFactory.getLogger(CategorizationProcessor.class);

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

            List<CollectionCategory> categories = responseContentLines.stream()
                    .map(line -> line.replaceFirst("^- ", ""))
                    .map(CollectionCategory::valueOf)
                    .distinct()
                    .toList();

            if (categories.size() < MIN_CATEGORY_NUM || categories.size() > MAX_CATEGORY_NUM) {
                throw new CategorizationResponseInvalidException(
                        "분류 결과는 " + MIN_CATEGORY_NUM + "개에서 + " + MAX_CATEGORY_NUM + "개 사이여야 합니다. ",
                        collectedContent.getId(), responseContent);
            }

            if (responseContentLines.size() != categories.size()) {
                log.debug("카테고리 분류 요청이 중복된 응답을 발생시켰습니다. contentId={}, responseContent={}, categories={}",
                        collectedContent.getId(), responseContent, categories);
            }
            return CategorizationResult.success(categories);
        } catch (Exception e) {
            return CategorizationResult.failure(e);
        }
    }
}
