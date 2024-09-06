package com.nocommittoday.techswipe.storage.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoggedInEntityJpaRepository extends JpaRepository<LoggedInEntity, Long> {

    Optional<LoggedInEntity> findByRefreshTokenId(String refreshTokenId);

    boolean existsByRefreshTokenId(String refreshTokenId);
}
