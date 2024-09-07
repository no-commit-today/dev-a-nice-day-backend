package com.nocommittoday.techswipe.infrastructure.alert;

import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

public class AlertCommand {

    private final String title;
    private final LocalDateTime occurredAt;
    @Nullable
    private final String content;
    @Nullable
    private final Exception ex;

    public AlertCommand(
            String title,
            LocalDateTime occurredAt,
            @Nullable String content,
            @Nullable Exception ex
    ) {
        this.title = title;
        this.occurredAt = occurredAt;
        this.content = content;
        this.ex = ex;
    }

    public static AlertCommandBuilder builder() {
        return new AlertCommandBuilder();
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    @Nullable
    public Exception getEx() {
        return ex;
    }

    @Nullable
    public String getExStackTrace() {
        if (ex == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    @Override
    public String toString() {
        return "AlertCommand{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", occurredAt=" + occurredAt +
                ", ex=" + ex +
                '}';
    }
}
