package com.nocommittoday.techswipe.storage.mysql.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.bookmark.Bookmark;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupId;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkId;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkQuery;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.core.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Objects;

@Entity
@Table(
        name = "bookmark",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_groupid_contentid", columnNames = {"group_id", "content_id"}),
        },
        indexes = {
                @Index(name = "ix_groupid_createdat", columnList = "group_id, created_at desc"),
        }
)
public class BookmarkEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BookmarkGroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "content_id", updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentEntity content;

    protected BookmarkEntity() {
    }

    public BookmarkEntity(BookmarkGroupEntity group, TechContentEntity content) {
        this.group = group;
        this.content = content;
    }

    public Bookmark toDomain() {
        return new Bookmark(
                new BookmarkId(id),
                new BookmarkGroupId(Objects.requireNonNull(group.getId())),
                new TechContentId(Objects.requireNonNull(content.getId()))
        );
    }

    public BookmarkQuery toQuery() {
        return new BookmarkQuery(
                new BookmarkId(id),
                group.toDomain(),
                content.toQuery()
        );
    }

    public Long getId() {
        return id;
    }

    public BookmarkGroupEntity getGroup() {
        return group;
    }

    public TechContentEntity getContent() {
        return content;
    }
}
