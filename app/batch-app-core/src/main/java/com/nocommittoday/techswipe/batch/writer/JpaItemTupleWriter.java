package com.nocommittoday.techswipe.batch.writer;

import jakarta.persistence.EntityManagerFactory;
import org.javatuples.Tuple;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.ArrayList;
import java.util.List;

public class JpaItemTupleWriter<T extends Tuple> extends JpaItemWriter<T> {

    private final JpaItemWriter<Object> jpaItemWriter;

    public JpaItemTupleWriter(JpaItemWriter<Object> jpaItemWriter) {
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(Chunk<? extends T> items) {
        List<Object> totalList = new ArrayList<>();

        for (Tuple item : items) {
            totalList.addAll(item.toList());
        }

        jpaItemWriter.write(new Chunk<>(totalList));
    }

    @Override
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
    }

    @Override
    public void setClearPersistenceContext(boolean clearPersistenceContext) {
        jpaItemWriter.setClearPersistenceContext(clearPersistenceContext);
    }

    @Override
    public void setUsePersist(boolean usePersist) {
        jpaItemWriter.setUsePersist(usePersist);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jpaItemWriter.afterPropertiesSet();
    }
}
