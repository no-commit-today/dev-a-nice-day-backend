package com.nocommittoday.techswipe.domain.rds.content.repository;

import java.util.Collection;
import java.util.List;

public interface TechPostRepositoryCustom {

    List<String> findAllUrlByUrlIn(Collection<String> urls);

    List<String> findAllUrl();
}
