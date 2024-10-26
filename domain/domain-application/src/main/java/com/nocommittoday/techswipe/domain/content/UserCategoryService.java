package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryService {

    private final UserCategorySaver userCategorySaver;
    private final UserCategoryReader userCategoryReader;

    public UserCategoryService(UserCategorySaver userCategorySaver, UserCategoryReader userCategoryReader) {
        this.userCategorySaver = userCategorySaver;
        this.userCategoryReader = userCategoryReader;
    }

    public void save(ApiUser apiUser, List<TechCategory> categories) {
        userCategorySaver.save(
                apiUser.getUserId(),
                categories.stream().distinct().sorted().toList()
        );
    }

    public UserCategoryList get(ApiUser apiUser) {
        return userCategoryReader.get(apiUser.getUserId());
    }
}
