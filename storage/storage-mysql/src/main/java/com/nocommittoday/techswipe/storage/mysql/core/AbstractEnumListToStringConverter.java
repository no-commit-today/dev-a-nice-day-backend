package com.nocommittoday.techswipe.storage.mysql.core;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractEnumListToStringConverter<T extends Enum<T>> implements AttributeConverter<List<T>, String> {

    private final Class<T> enumType;

    protected AbstractEnumListToStringConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        if (attribute == null) {
            return null;
        }

        return "[" +
                attribute.stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","))
                + "]";
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        String categories = dbData.substring(1, dbData.length() - 1);
        if (categories.isEmpty()) {
            return List.of();
        }

        return Arrays.stream(categories.split(","))
                .map(category -> Enum.valueOf(enumType, category))
                .toList();
    }
}
