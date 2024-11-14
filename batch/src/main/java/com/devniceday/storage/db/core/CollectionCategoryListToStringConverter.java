package com.devniceday.storage.db.core;


import com.devniceday.batch.domain.CollectionCategory;

public class CollectionCategoryListToStringConverter extends AbstractEnumListToStringConverter<CollectionCategory> {

    public CollectionCategoryListToStringConverter() {
        super(CollectionCategory.class);
    }
}
