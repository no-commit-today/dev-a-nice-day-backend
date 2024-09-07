package com.nocommittoday.techswipe.infrastructure.alert;

import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class AlertCommandBuilder {

    private String title;

    @Nullable
    private LocalDateTime occurredAt;

    @Nullable
    private String content;

    @Nullable
    private Exception ex;

    public AlertCommandBuilder() {
    }

    public AlertCommandBuilder title(String title) {
        this.title = title;
        return this;
    }

    public AlertCommandBuilder occurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public AlertCommandBuilder content(String content) {
        this.content = content;
        return this;
    }

    public AlertCommandBuilder ex(Exception ex) {
        this.ex = ex;
        return this;
    }

    public AlertCommand build() {
        Assert.notNull(title, "title 은 필수입니다.");
        if (occurredAt == null) {
            occurredAt = LocalDateTime.now();
        }
        return new AlertCommand(title, occurredAt, content, ex);
    }
}
