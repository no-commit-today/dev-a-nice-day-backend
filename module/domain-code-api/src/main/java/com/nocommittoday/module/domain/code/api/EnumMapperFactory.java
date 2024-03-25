package com.nocommittoday.module.domain.code.api;

import com.nocommittoday.module.domain.code.EnumMapperType;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumMapperFactory {

    private final Map<String, List<EnumMapperValue>> factory = new LinkedHashMap<>();

    public void put(final Class<? extends EnumMapperType> e) {
        put(e.getSimpleName(), e);
    }

    public void put(final String key, final Class<? extends EnumMapperType> e) {
        factory.put(key, toEnumValues(e));
    }

    private List<EnumMapperValue> toEnumValues(final Class<? extends EnumMapperType> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(EnumMapperValue::of)
                .toList();
    }

    public List<EnumMapperValue> get(final String key) {
        return factory.getOrDefault(key, List.of());
    }

    public Map<String, List<EnumMapperValue>> get(final List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return Map.of();
        }

        return keys.stream()
                .collect(Collectors.toMap(k -> k, this::get));
    }

    public Map<String, List<EnumMapperValue>> getAll() {
        return Collections.unmodifiableMap(factory);
    }
}
