package com.nocommittoday.techswipe.storage.mysql;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * 실제 데이터 확인용 테스트
 */
@Tag("develop")
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.url=jdbc:mysql://localhost:3306/techswipe",
        "spring.datasource.username=root",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect"
})
class StorageMysqlLocalSearchRealDataDevelopTest {

    @Autowired
    private EntityManager em;

    @Test
    void practice() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    }

}
