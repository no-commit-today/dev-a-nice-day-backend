package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class CategoryListConverter implements AttributeConverter<List<CollectionCategory>, String> {

    @Nullable
    @Override
    public String convertToDatabaseColumn(@Nullable List<CollectionCategory> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(CollectionCategory::name)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(","));
    }

    @Nullable
    @Override
    public List<CollectionCategory> convertToEntityAttribute(@Nullable String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(CollectionCategory::valueOf)
                .toList();
    }
}
