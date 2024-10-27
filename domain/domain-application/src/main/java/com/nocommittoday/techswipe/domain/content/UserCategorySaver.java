package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryEntityEditor;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentUserCategoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCategorySaver {

    private final TechContentUserCategoryJpaRepository techContentUserCategoryJpaRepository;

    public UserCategorySaver(
            TechContentUserCategoryJpaRepository techContentUserCategoryJpaRepository
    ) {
        this.techContentUserCategoryJpaRepository = techContentUserCategoryJpaRepository;
    }

    public void save(UserId userId, List<TechCategory> categories) {
        techContentUserCategoryJpaRepository.findByUserId(userId.value())
                .ifPresentOrElse(
                        entity -> {
                            TechContentUserCategoryEntityEditor editor = entity.toEditor();
                            editor.setCategories(categories);
                            entity.edit(editor);
                            techContentUserCategoryJpaRepository.save(entity);
                        },
                        () -> techContentUserCategoryJpaRepository
                                .save(new TechContentUserCategoryEntity(userId.value(), categories))
                );
    }
}
