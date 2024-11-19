package com.devniceday.batch.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscribedContentFetcher {

    private final List<SubscribedContentFetchCommand> commands;

    public SubscribedContentFetcher(List<SubscribedContentFetchCommand> commands) {
        this.commands = commands;
    }

    public List<SubscribedContent> fetch(Subscription subscription, int page) {
        return commands.stream()
                .filter(command -> command.supports(subscription))
                .findFirst()
                .map(command -> command.fetch(subscription, page))
                .orElseThrow(() -> new IllegalStateException("지원하지 않는 구독 타입입니다. subscription=" + subscription));
    }
}
