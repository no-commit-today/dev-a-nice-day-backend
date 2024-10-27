package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCategoryReader {

    private final TechContentUserCategoryJpaRepository techContentUserCategoryJpaRepository;

    public UserCategoryReader(TechContentUserCategoryJpaRepository techContentUserCategoryJpaRepository) {
        this.techContentUserCategoryJpaRepository = techContentUserCategoryJpaRepository;
    }

    public UserCategoryList get(UserId userId) {
        return techContentUserCategoryJpaRepository.findByUserId(userId.value())
                .map(entity -> new UserCategoryList(userId, entity.getCategories()))
                .orElse(new UserCategoryList(userId, List.of()));
    }
}
