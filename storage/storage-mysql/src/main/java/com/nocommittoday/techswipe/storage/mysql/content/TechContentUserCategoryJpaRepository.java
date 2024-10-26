package com.nocommittoday.techswipe.storage.mysql.content;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechContentUserCategoryJpaRepository extends JpaRepository<TechContentUserCategoryEntity, Long> {

    Optional<TechContentUserCategoryEntity> findByUserId(Long userId);
}
