package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserCategoryServiceTest {

    @InjectMocks
    private UserCategoryService userCategoryService;

    @Mock
    private UserCategorySaver userCategorySaver;

    @Test
    void 유저_카테고리를_저장할_때_중복을_제거하고_정렬한다() {
        // given
        ApiUser apiUser = new ApiUser(new UserId(123L));
        List<TechCategory> categories = List.of(
                TechCategory.WEB,
                TechCategory.WEB,
                TechCategory.SW_ENGINEERING,
                TechCategory.SW_ENGINEERING,
                TechCategory.SERVER
        );

        // when
        userCategoryService.save(apiUser, categories);

        // then
        then(userCategorySaver).should().save(
                apiUser.getUserId(),
                List.of(TechCategory.SERVER, TechCategory.WEB, TechCategory.SW_ENGINEERING)
        );
    }
}