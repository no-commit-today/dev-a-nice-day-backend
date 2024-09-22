package com.nocommittoday.techswipe.storage.mysql.bookmark;

import com.nocommittoday.techswipe.domain.bookmark.BookmarkGroup;
import com.nocommittoday.techswipe.domain.bookmark.BookmarkGroupId;
import com.nocommittoday.techswipe.domain.bookmark.BookmarkGroupQuery;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.core.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "bookmark_group",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_userid_name", columnNames = {"user_id", "name"}),
        }
)
public class BookmarkGroupEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    protected BookmarkGroupEntity() {
    }

    public BookmarkGroupEntity(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public BookmarkGroup toDomain() {
        return new BookmarkGroup(
                new BookmarkGroupId(id),
                new UserId(userId),
                name
        );
    }

    public BookmarkGroupQuery toQuery() {
        return new BookmarkGroupQuery(
                new BookmarkGroupId(id),
                new UserId(userId),
                name
        );
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
