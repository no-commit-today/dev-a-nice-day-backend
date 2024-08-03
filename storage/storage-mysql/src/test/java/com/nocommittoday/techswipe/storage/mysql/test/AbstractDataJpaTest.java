package com.nocommittoday.techswipe.storage.mysql.test;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Tag("context")
@DataJpaTest
@Import(TestJpaConfig.class)
@Transactional
public abstract class AbstractDataJpaTest {

    @Autowired
    protected EntityManager em;

    @Autowired
    protected LocalAutoIncrementIdGenerator idGenerator;
}
