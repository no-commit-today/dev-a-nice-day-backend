package com.nocommittoday.module.lock.rds;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<TestEntity> findWithOptimisticLockById(Long id);
}
