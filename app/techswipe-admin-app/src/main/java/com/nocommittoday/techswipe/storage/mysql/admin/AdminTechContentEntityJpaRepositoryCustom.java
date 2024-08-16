package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.domain.content.TechCategory;

import java.util.List;

public interface AdminTechContentEntityJpaRepositoryCustom {

    boolean updateCategoriesById(Long id, List<TechCategory> categories);
}
