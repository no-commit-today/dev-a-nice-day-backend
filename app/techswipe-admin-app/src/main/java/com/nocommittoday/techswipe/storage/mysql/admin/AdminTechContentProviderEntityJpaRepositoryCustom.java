package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;

public interface AdminTechContentProviderEntityJpaRepositoryCustom {

    long updateIconById(Long id, ImageEntity icon);
}
