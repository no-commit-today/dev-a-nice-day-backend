package com.devniceday.reader;

import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.EntityTransaction;
import org.springframework.util.ClassUtils;

/**
 * https://github.com/jojoldu/spring-batch-querydsl
 */
public class QuerydslZeroPagingItemReader<T> extends QuerydslPagingItemReader<T> {

    public QuerydslZeroPagingItemReader() {
        super();
        setName(ClassUtils.getShortName(QuerydslZeroPagingItemReader.class));
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void doReadPage() {

        EntityTransaction tx = getTxOrNull();

        JPQLQuery<T> query = createQuery()
                .offset(0)
                .limit(getPageSize());

        initResults();

        fetchQuery(query, tx);
    }

}
