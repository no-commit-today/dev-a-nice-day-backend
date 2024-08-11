package com.nocommittoday.techswipe.storage.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedInEntityJpaRepository extends JpaRepository<LoggedInEntity, Long> {
}
