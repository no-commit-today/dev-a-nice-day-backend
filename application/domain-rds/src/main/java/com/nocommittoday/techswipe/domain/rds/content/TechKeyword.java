package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.techswipe.domain.rds.core.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "tech_keyword",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_keyword__name", columnNames = {"name"})
        }
)
public class TechKeyword extends BaseTimeEntity {

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    protected TechKeyword() {
    }

    public TechKeyword(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
