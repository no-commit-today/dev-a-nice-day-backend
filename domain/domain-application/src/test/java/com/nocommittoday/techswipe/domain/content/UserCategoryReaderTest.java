package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserCategoryReaderTest {

    @InjectMocks
    private UserCategoryReader userCategoryReader;

    @Mock
    private TechContentUserCategoryJpaRepository techContentUserCategoryJpaRepository;

    @Test
    void 유저_카테고리를_조회한다() {
        // given
        UserId userId = new UserId(1L);
        given(techContentUserCategoryJpaRepository.findByUserId(userId.value()))
                .willReturn(Optional.of(new TechContentUserCategoryEntity(
                        1L, List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING))));

        // when
        UserCategoryList userCategoryList = userCategoryReader.get(userId);

        // then
        assertThat(userCategoryList.getCategories()).containsExactly(TechCategory.SERVER, TechCategory.SW_ENGINEERING);
    }

    @Test
    void 유저_카테고리가_없으면_빈_리스트를_반환한다() {
        // given
        UserId userId = new UserId(1L);
        given(techContentUserCategoryJpaRepository.findByUserId(userId.value()))
                .willReturn(Optional.empty());

        // when
        UserCategoryList userCategoryList = userCategoryReader.get(userId);

        // then
        assertThat(userCategoryList.getCategories()).isEmpty();
    }
}