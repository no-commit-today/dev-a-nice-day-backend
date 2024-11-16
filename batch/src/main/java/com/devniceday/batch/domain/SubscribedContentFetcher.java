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
        for (SubscribedContentFetchCommand command : commands) {
            if (command.supports(subscription)) {
                return command.fetch(subscription, page);
            }
        }

        throw new IllegalStateException("지원하는 커맨드가 없습니다. subscription=" + subscription +
                ", command 타입 = " + commands.stream().map(Object::getClass).toList());
    }
}
