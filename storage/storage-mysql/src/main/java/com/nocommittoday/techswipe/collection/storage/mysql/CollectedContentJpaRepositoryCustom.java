package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;

import java.util.List;

interface CollectedContentJpaRepositoryCustom {

    List<String> findAllUrlByProvider(TechContentProviderEntity provider);
}
