package com.devniceday.batch.domain;

import com.devniceday.core.enums.EnumMapperType;
import com.devniceday.core.enums.TechCategory;
import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum CollectionCategory implements EnumMapperType {

    SERVER("서버 개발", TechCategory.SERVER),
    WEB("웹 개발", TechCategory.WEB),
    APP("앱 개발", TechCategory.APP),
    DATA_ENGINEERING("데이터 엔지니어링", TechCategory.DATA_ENGINEERING),
    DEVOPS("데브옵스", TechCategory.DEVOPS),
    AI("인공지능", TechCategory.AI),
    SW_ENGINEERING("SW 엔지니어링", TechCategory.SW_ENGINEERING),
    DEV_TOOL("개발 툴", TechCategory.DEV_TOOL),

    NON_DEV("개발 외 주제", null),
    ILLEGAL_CONTENT("잘못된 컨텐츠", null),
    ;

    private final String title;
    private final TechCategory techCategory;

    CollectionCategory(String title, @Nullable TechCategory techCategory) {
        this.title = title;
        this.techCategory = techCategory;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public TechCategory toTechCategory() {
        if (techCategory == null) {
            throw new IllegalStateException("techCategory is null");
        }
        return techCategory;
    }

    public boolean isUsed() {
        return techCategory != null;
    }

    public static final int MIN_LIST_SIZE = 1;
    public static final int MAX_LIST_SIZE = 3;

    private static final String PATTERN_CATEGORY_GROUP_NAME = "category";
    private static final Pattern CHAT_COMPLETION_RESULT_PATTERN = Pattern.compile("^- (" +
            "?<" + PATTERN_CATEGORY_GROUP_NAME + ">"
            + Arrays.stream(CollectionCategory.values())
            .map(CollectionCategory::name)
            .collect(Collectors.joining("|"))
            + ")$");

    @Nullable
    public static List<CollectionCategory> createListFromChatCompletionResult(String chatCompletionResult) {

        List<String> lines = Arrays.stream(chatCompletionResult.split("\n"))
                .filter(message -> !message.isBlank())
                .map(String::trim)
                .toList();
        Set<CollectionCategory> categories = new HashSet<>();
        for (String line : lines) {
            Matcher matcher = CHAT_COMPLETION_RESULT_PATTERN.matcher(line);
            if (!matcher.matches()) {
                return null;
            }
            categories.add(CollectionCategory.valueOf(matcher.group(PATTERN_CATEGORY_GROUP_NAME)));
        }

        if (!(MIN_LIST_SIZE <= categories.size() && categories.size() <= MAX_LIST_SIZE)) {
            return null;
        }
        return categories.stream().sorted(Comparator.comparing(Enum::name)).toList();
    }
}
