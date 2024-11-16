package com.devniceday.module.alert;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class AlertCommandBuilder {

    private String title;

    @Nullable
    private AlertType alertType;

    @Nullable
    private String content;

    @Nullable
    private Exception ex;

    public AlertCommandBuilder warn() {
        this.alertType = AlertType.WARN;
        return this;
    }

    public AlertCommandBuilder error() {
        this.alertType = AlertType.ERROR;
        return this;
    }

    public AlertCommandBuilder title(String title) {
        this.title = title;
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
        if (title == null) {
            throw new IllegalArgumentException("title 은 필수입니다.");
        }
        if (alertType != null) {
            title = alertType.getEmoji() + " " + title;
        }
        return new AlertCommand(title, content, ex);
    }


    private enum AlertType {
        WARN("⚠️"),
        ERROR("🚨")
        ;

        private final String emoji;

        AlertType(String emoji) {
            this.emoji = emoji;
        }

        public String getEmoji() {
            return emoji;
        }
    }
}
