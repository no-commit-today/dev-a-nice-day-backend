package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;

import java.util.List;

public class TechContentUserCategoryEntityEditor {

    private List<TechCategory> categories;

    public TechContentUserCategoryEntityEditor(List<TechCategory> categories) {
        this.categories = categories;
    }

    public List<TechCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<TechCategory> categories) {
        this.categories = categories;
    }
}
