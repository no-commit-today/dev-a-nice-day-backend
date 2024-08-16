package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionIllegalCategoryException;
import com.nocommittoday.techswipe.domain.content.TechCategory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CollectionCategoryList {

    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 3;

    private final List<CollectionCategory> categories;

    public CollectionCategoryList(List<CollectionCategory> categories) {
        this.categories = Collections.unmodifiableList(categories);
    }

    public static CollectionCategoryList create(Collection<CollectionCategory> categories) {
        if (categories.size() < MIN_SIZE || categories.size() > MAX_SIZE) {
            throw new CollectionIllegalCategoryException(categories);
        }
        return new CollectionCategoryList(
                categories.stream().distinct().sorted().toList()
        );
    }

    public boolean containsUnused() {
        return categories.stream()
                .anyMatch(category -> !category.isUsed());
    }

    public List<TechCategory> toTechCategories() {
        return categories.stream()
                .map(category -> Objects.requireNonNull(category.getTechCategory()))
                .toList();
    }

    public List<CollectionCategory> getCategories() {
        return categories;
    }
}
