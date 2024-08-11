package com.nocommittoday.techswipe.storage.mysql.user;

import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import javax.annotation.Nullable;

@Entity
@Table(
        name = "users"
)
public class UserEntity extends BaseSoftDeleteEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected UserEntity() {
    }

    public UserEntity(@Nullable Long id) {
        this.id = id;
    }
}
