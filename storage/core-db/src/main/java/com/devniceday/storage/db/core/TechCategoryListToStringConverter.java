package com.devniceday.storage.db.core;


import com.devniceday.core.enums.TechCategory;

public class TechCategoryListToStringConverter extends AbstractEnumListToStringConverter<TechCategory> {

    public TechCategoryListToStringConverter() {
        super(TechCategory.class);
    }
}
