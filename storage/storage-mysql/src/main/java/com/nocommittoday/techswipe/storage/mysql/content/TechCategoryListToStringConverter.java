package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.storage.mysql.core.AbstractEnumListToStringConverter;

public class TechCategoryListToStringConverter extends AbstractEnumListToStringConverter<TechCategory> {

    public TechCategoryListToStringConverter() {
        super(TechCategory.class);
    }
}
