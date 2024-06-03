package com.nocommittoday.techswipe.collection.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class CategoryListConverter implements AttributeConverter<List<TechCategory>, String> {

    @Nullable
    @Override
    public String convertToDatabaseColumn(@Nullable final List<TechCategory> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(TechCategory::name)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(","));
    }

    @Nullable
    @Override
    public List<TechCategory> convertToEntityAttribute(@Nullable final String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(TechCategory::valueOf)
                .toList();
    }
}
