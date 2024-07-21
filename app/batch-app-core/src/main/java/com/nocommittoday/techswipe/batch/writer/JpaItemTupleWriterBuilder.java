package com.nocommittoday.techswipe.batch.writer;

import jakarta.persistence.EntityManagerFactory;
import org.javatuples.Tuple;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;

public class JpaItemTupleWriterBuilder<T extends Tuple> {

    private final JpaItemWriterBuilder<Object> jpaItemWriterBuilder = new JpaItemWriterBuilder<>();

    public JpaItemTupleWriterBuilder<T> entityManagerFactory(final EntityManagerFactory entityManagerFactory) {
        jpaItemWriterBuilder.entityManagerFactory(entityManagerFactory);
        return this;
    }

    public JpaItemTupleWriterBuilder<T> usePersist(final boolean usePersist) {
        jpaItemWriterBuilder.usePersist(usePersist);
        return this;
    }

    public JpaItemTupleWriterBuilder<T> clearPersistenceContext(final boolean clearPersistenceContext) {
        jpaItemWriterBuilder.clearPersistenceContext(clearPersistenceContext);
        return this;
    }

    public JpaItemTupleWriter<T> build() {
        final JpaItemWriter<Object> jpaItemWriter = jpaItemWriterBuilder.build();
        return new JpaItemTupleWriter<>(jpaItemWriter);
    }
}
