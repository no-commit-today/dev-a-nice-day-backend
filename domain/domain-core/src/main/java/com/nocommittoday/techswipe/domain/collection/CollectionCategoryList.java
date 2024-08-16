package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionIllegalCategoryException;
import com.nocommittoday.techswipe.domain.content.TechCategory;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        List<CollectionCategory> list = categories.stream()
                .distinct()
                .sorted(Comparator.comparing(Enum::name))
                .toList();

        if (!(MIN_SIZE <= list.size() && list.size() <= MAX_SIZE)) {
            throw new CollectionIllegalCategoryException(list);
        }
        return new CollectionCategoryList(
                list
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
