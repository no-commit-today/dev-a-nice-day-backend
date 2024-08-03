package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;

import java.util.Collection;
import java.util.List;

interface CollectedContentJpaRepositoryCustom {

    List<String> findAllUrl();

    List<String> findAllUrlByProvider(TechContentProviderEntity provider);

    List<String> findAllUrlByProviderIn(Collection<TechContentProviderEntity> providers);

    List<String> findAllUrlByUrlIn(Collection<String> urls);
}
