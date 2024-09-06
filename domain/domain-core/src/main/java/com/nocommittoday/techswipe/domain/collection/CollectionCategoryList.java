package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.DomainValidationException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CollectionCategoryList {

    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 3;
    private static final String PATTERN_CATEGORY_GROUP_NAME = "category";
    private static final Pattern CHAT_COMPLETION_RESULT_PATTERN = Pattern.compile("^- (" +
            "?<" + PATTERN_CATEGORY_GROUP_NAME + ">"
            + Arrays.stream(CollectionCategory.values())
            .map(CollectionCategory::name)
            .collect(Collectors.joining("|"))
            + ")$");

    private final List<CollectionCategory> content;

    public CollectionCategoryList(List<CollectionCategory> content) {
        this.content = Collections.unmodifiableList(content);
    }

    public static CollectionCategoryList create(String chatCompletionResult) {
        List<String> lines = Arrays.stream(chatCompletionResult.split("\n"))
                .filter(message -> !message.isBlank())
                .map(String::trim)
                .toList();

        List<CollectionCategory> categories = lines.stream()
                .map(line -> {
                    Matcher matcher = CHAT_COMPLETION_RESULT_PATTERN.matcher(line);
                    if (!matcher.matches()) {
                        throw new DomainValidationException("chatCompletionResult=" + chatCompletionResult);
                    }
                    return CollectionCategory.valueOf(matcher.group(PATTERN_CATEGORY_GROUP_NAME));
                })
                .toList();

        return create(categories);
    }

    public static CollectionCategoryList create(Collection<CollectionCategory> categories) {
        List<CollectionCategory> list = categories.stream()
                .distinct()
                .sorted(Comparator.comparing(Enum::name))
                .toList();

        if (!(MIN_SIZE <= list.size() && list.size() <= MAX_SIZE)) {
            throw new DomainValidationException("카테고리 개수는 " + MIN_SIZE + " 이상 " + MAX_SIZE + " 이하여야 합니다.", list);
        }

        return new CollectionCategoryList(list);
    }

    public boolean containsUnused() {
        return content.stream()
                .anyMatch(category -> !category.isUsed());
    }

    public List<TechCategory> toTechCategories() {
        return content.stream()
                .map(CollectionCategory::toTechCategory)
                .toList();
    }

    public List<CollectionCategory> getContent() {
        return content;
    }
}
