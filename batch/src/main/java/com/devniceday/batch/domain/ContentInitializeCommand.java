package com.devniceday.batch.domain;

public interface ContentInitializeCommand {

    boolean supports(Subscription subscription);

    ContentInitialization initialize(Subscription subscription, String url);
}
