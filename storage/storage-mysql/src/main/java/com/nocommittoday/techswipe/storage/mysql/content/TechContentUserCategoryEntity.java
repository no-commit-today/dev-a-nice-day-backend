package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.storage.mysql.core.BaseTimeEntity;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.List;

@Entity
@Table(
        name = "tech_content_user_category",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_userid", columnNames = "user_id")
        }
)
public class TechContentUserCategoryEntity extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @Convert(converter = TechCategoryListToStringConverter.class)
    @Column(name = "categories", length = 500)
    private List<TechCategory> categories;

    protected TechContentUserCategoryEntity() {
    }

    public TechContentUserCategoryEntity(
            UserEntity user,
            List<TechCategory> categories
    ) {
        this.user = user;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public List<TechCategory> getCategories() {
        return categories;
    }
}
