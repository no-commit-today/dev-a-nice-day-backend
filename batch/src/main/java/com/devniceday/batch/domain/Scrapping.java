package com.devniceday.batch.domain;

import jakarta.annotation.Nullable;

import java.util.List;


public record Scrapping(
        ScrappingType type,
        @Nullable List<Integer> indexes,
        @Nullable String selector
) {
}
