package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public class CollectedContentEntityEditor {

    private CollectionStatus status;
    @Nullable
    private TechContentEntity publishedContent;
    @Nullable
    private String title;
    @Nullable
    private LocalDate publishedDate;
    @Nullable
    private String content;
    @Nullable
    private String imageUrl;
    @Nullable
    private List<CollectionCategory> categories;
    @Nullable
    private String summary;

    public CollectedContentEntityEditor(
            CollectionStatus status,
            @Nullable TechContentEntity publishedContent,
            @Nullable String title,
            @Nullable LocalDate publishedDate,
            @Nullable String content,
            @Nullable String imageUrl,
            @Nullable List<CollectionCategory> categories,
            @Nullable String summary
    ) {
        this.status = status;
        this.publishedContent = publishedContent;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.summary = summary;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    public void setStatus(CollectionStatus status) {
        this.status = status;
    }

    @Nullable
    public TechContentEntity getPublishedContent() {
        return publishedContent;
    }

    public void setPublishedContent(@Nullable TechContentEntity publishedContent) {
        this.publishedContent = publishedContent;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(@Nullable LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public List<CollectionCategory> getCategories() {
        return categories;
    }

    public void setCategories(@Nullable List<CollectionCategory> categories) {
        this.categories = categories;
    }

    @Nullable
    public String getSummary() {
        return summary;
    }

    public void setSummary(@Nullable String summary) {
        this.summary = summary;
    }
}
