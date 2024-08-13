package com.nocommittoday.techswipe.storage.mysql.test;

import com.nocommittoday.techswipe.domain.test.LocalAutoIncrementIdUtils;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntity;

import javax.annotation.Nullable;

public class UserEntityBuilder {

    @Nullable
    private Long id;

    public UserEntityBuilder() {
    }

    public static UserEntity create() {
        return create(false);
    }

    public static UserEntity create(boolean withId) {
        return new UserEntityBuilder().build(withId);
    }

    public UserEntityBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserEntity build() {
        return build(false);
    }

    public UserEntity build(boolean withId) {
        fillRequiredFields(withId);
        return new UserEntity(
            id
        );
    }

    private void fillRequiredFields(boolean withId) {
        if (withId && id == null) {
            id = LocalAutoIncrementIdUtils.nextId();
        }
    }
}
