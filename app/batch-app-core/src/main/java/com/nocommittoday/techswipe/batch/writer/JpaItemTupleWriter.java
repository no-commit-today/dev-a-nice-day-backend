package com.nocommittoday.techswipe.batch.writer;

import jakarta.persistence.EntityManagerFactory;
import org.javatuples.Tuple;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.ArrayList;
import java.util.List;

public class JpaItemTupleWriter<T extends Tuple> extends JpaItemWriter<T> {

    private final JpaItemWriter<Object> jpaItemWriter;

    public JpaItemTupleWriter(final JpaItemWriter<Object> jpaItemWriter) {
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(final Chunk<? extends T> items) {
        final List<Object> totalList = new ArrayList<>();

        for (Tuple item : items) {
            totalList.addAll(item.toList());
        }

        jpaItemWriter.write(new Chunk<>(totalList));
    }

    @Override
    public void setEntityManagerFactory(final EntityManagerFactory entityManagerFactory) {
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
    }

    @Override
    public void setClearPersistenceContext(final boolean clearPersistenceContext) {
        jpaItemWriter.setClearPersistenceContext(clearPersistenceContext);
    }

    @Override
    public void setUsePersist(final boolean usePersist) {
        jpaItemWriter.setUsePersist(usePersist);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jpaItemWriter.afterPropertiesSet();
    }
}
