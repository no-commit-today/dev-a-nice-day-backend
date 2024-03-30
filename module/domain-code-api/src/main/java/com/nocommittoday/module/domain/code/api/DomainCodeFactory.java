package com.nocommittoday.module.domain.code.api;

import com.nocommittoday.module.domain.code.DomainCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DomainCodeFactory {

    private final Map<String, List<DomainCodeValue>> factory = new LinkedHashMap<>();

    public void put(final Class<? extends DomainCode> e) {
        put(e.getSimpleName(), e);
    }

    public void put(final String key, final Class<? extends DomainCode> e) {
        factory.put(key, toEnumValues(e));
    }

    private List<DomainCodeValue> toEnumValues(final Class<? extends DomainCode> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(DomainCodeValue::of)
                .toList();
    }

    public List<DomainCodeValue> get(final String key) {
        return factory.getOrDefault(key, List.of());
    }

    public Map<String, List<DomainCodeValue>> get(final List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return Map.of();
        }

        return keys.stream()
                .collect(Collectors.toMap(k -> k, this::get));
    }

    public Map<String, List<DomainCodeValue>> getAll() {
        return Collections.unmodifiableMap(factory);
    }
}
