package com.nocommittoday.techswipe.storage.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityJpaRepository extends JpaRepository<UserEntity, Long> {
}
