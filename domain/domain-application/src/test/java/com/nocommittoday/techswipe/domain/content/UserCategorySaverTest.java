package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserCategorySaverTest {

    @InjectMocks
    private UserCategorySaver userCategorySaver;

    @Mock
    private TechContentUserCategoryJpaRepository techContentUserCategoryJpaRepository;

    @Captor
    private ArgumentCaptor<TechContentUserCategoryEntity> captor;

    @Test
    void 유저_카테고리를_수정한다() {
        // given
        UserId userId = new UserId(123L);
        TechContentUserCategoryEntity entity = new TechContentUserCategoryEntity(
                userId.value(),
                List.of(TechCategory.SERVER)
        );
        given(techContentUserCategoryJpaRepository.findByUserId(userId.value()))
                .willReturn(Optional.of(entity));

        // when
        userCategorySaver.save(userId, List.of(TechCategory.WEB, TechCategory.SW_ENGINEERING));

        // then
        assertThat(entity.getCategories())
                .containsExactly(TechCategory.WEB, TechCategory.SW_ENGINEERING);
        then(techContentUserCategoryJpaRepository).should().save(entity);
    }

    @Test
    void 유저_카테고리가_없으면_생성한다() {
        // given
        UserId userId = new UserId(123L);
        given(techContentUserCategoryJpaRepository.findByUserId(userId.value()))
                .willReturn(Optional.empty());

        // when
        userCategorySaver.save(userId, List.of(TechCategory.WEB, TechCategory.SW_ENGINEERING));

        // then
        then(techContentUserCategoryJpaRepository).should().save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo(userId.value());
        assertThat(captor.getValue().getCategories())
                .containsExactly(TechCategory.WEB, TechCategory.SW_ENGINEERING);
    }
}