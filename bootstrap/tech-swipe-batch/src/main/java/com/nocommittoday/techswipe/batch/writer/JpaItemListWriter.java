package com.nocommittoday.techswipe.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.ArrayList;
import java.util.List;

public class JpaItemListWriter<T> extends JpaItemWriter<List<T>> {

    private final JpaItemWriter<T> jpaItemWriter;

    public JpaItemListWriter(final JpaItemWriter<T> jpaItemWriter) {
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(final Chunk<? extends List<T>> items) {
        final List<T> totalList = new ArrayList<>();

        for (List<T> list : items) {
            totalList.addAll(list);
        }

        jpaItemWriter.write(new Chunk<>(totalList));
    }
}
