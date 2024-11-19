package com.devniceday.batch.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContentInitializer {

    private final List<ContentInitializeCommand> commands;

    public ContentInitializer(List<ContentInitializeCommand> commands) {
        this.commands = commands;
    }

    public ContentInitialization initialize(Subscription subscription, String url) {
        return commands.stream()
                .filter(command -> command.supports(subscription))
                .findFirst()
                .map(command -> command.initialize(subscription, url))
                .orElseThrow(() -> new IllegalStateException("지원하지 않는 구독 타입입니다. subscription=" + subscription));
    }
}
