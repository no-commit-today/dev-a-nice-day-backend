package com.nocommittoday.module.lock.rds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "test_entity")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    private Integer count = 0;

    protected TestEntity() {
    }

    public void incrementCount() {
        count += 1;
    }

    public Long getId() {
        return id;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", version=" + version +
                ", count=" + count +
                '}';
    }
}
