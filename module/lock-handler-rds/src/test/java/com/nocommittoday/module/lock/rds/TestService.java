package com.nocommittoday.module.lock.rds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    private static Logger log = LoggerFactory.getLogger(TestService.class);

    private final TestRepository repository;

    public TestService(final TestRepository repository) {
        this.repository = repository;
    }

    public Long create() {
        return repository.save(new TestEntity()).getId();
    }

    public TestEntity get(final Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void incrementCount(final Long id) {
        final TestEntity entity = repository.findWithOptimisticLockById(id).orElseThrow();
        entity.incrementCount();
        log.info("incrementCount >> {}", entity);
    }
}
