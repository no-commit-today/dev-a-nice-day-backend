package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;

import javax.annotation.Nullable;
import java.util.List;

public class CollectionCategoryListBuilder {

    @Nullable
    private List<CollectionCategory> content;

    public CollectionCategoryListBuilder() {
    }

    public static CollectionCategoryList create() {
        return new CollectionCategoryListBuilder().build();
    }

    public CollectionCategoryListBuilder content(List<CollectionCategory> content) {
        this.content = content;
        return this;
    }

    public CollectionCategoryList build() {
        fillRequiredFields();
        return CollectionCategoryList.create(content);
    }

    private void fillRequiredFields() {

        if (content == null) {
            content = List.of(CollectionCategory.SERVER, CollectionCategory.SW_ENGINEERING);
        }
    }
}
