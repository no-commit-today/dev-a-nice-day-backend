package com.nocommittoday.techswipe.storage.mysql.admin;

interface AdminCollectedContentEntityJpaRepositoryCustom {

    boolean updateSummaryById(Long id, String summary);
}
