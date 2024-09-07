package com.nocommittoday.techswipe.infrastructure.alert;

public class DiscordWebhookRequest {

    private static final int MAX_CONTENT_LENGTH = 2000;

    private final String content;

    public DiscordWebhookRequest(String content) {
        this.content = content;
    }

    public static DiscordWebhookRequest create(String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            return new DiscordWebhookRequest(content.substring(0, MAX_CONTENT_LENGTH - 4) + "\n...");
        }
        return new DiscordWebhookRequest(content);
    }

    public static DiscordWebhookRequest from(AlertCommand alertCommand) {
        StringBuilder sb = new StringBuilder();

        sb.append("# ").append(alertCommand.getTitle()).append("\n");

        sb.append("[발생 시각]\n").append(alertCommand.getOccurredAt()).append("\n\n");

        if (alertCommand.getContent() != null) {
            sb.append("[내용]\n").append(alertCommand.getContent()).append("\n\n");
        }

        if (alertCommand.getEx() != null) {
            sb.append("[예외]\n").append(alertCommand.getExStackTrace()).append("\n\n");
        }

        return create(sb.toString());
    }

    public String getContent() {
        return content;
    }
}
