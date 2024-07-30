package com.nocommittoday.techswipe.batch.writer;

import jakarta.persistence.EntityManagerFactory;
import org.javatuples.Tuple;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;

public class JpaItemTupleWriterBuilder<T extends Tuple> {

    private final JpaItemWriterBuilder<Object> jpaItemWriterBuilder = new JpaItemWriterBuilder<>();

    public JpaItemTupleWriterBuilder<T> entityManagerFactory(EntityManagerFactory entityManagerFactory) {
        jpaItemWriterBuilder.entityManagerFactory(entityManagerFactory);
        return this;
    }

    public JpaItemTupleWriterBuilder<T> usePersist(boolean usePersist) {
        jpaItemWriterBuilder.usePersist(usePersist);
        return this;
    }

    public JpaItemTupleWriterBuilder<T> clearPersistenceContext(boolean clearPersistenceContext) {
        jpaItemWriterBuilder.clearPersistenceContext(clearPersistenceContext);
        return this;
    }

    public JpaItemTupleWriter<T> build() {
        JpaItemWriter<Object> jpaItemWriter = jpaItemWriterBuilder.build();
        return new JpaItemTupleWriter<>(jpaItemWriter);
    }
}
