package com.nocommittoday.techswipe.storage.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2UserEntityJpaRepository extends JpaRepository<OAuth2UserEntity, Long> {
}
