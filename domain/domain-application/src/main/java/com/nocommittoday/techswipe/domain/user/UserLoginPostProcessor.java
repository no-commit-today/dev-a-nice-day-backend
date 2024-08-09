package com.nocommittoday.techswipe.domain.user;

import org.springframework.stereotype.Component;

@Component
public class UserLoginPostProcessor {

    public void process(User user, RefreshToken refreshToken) {
        // TODO: implement
        // user 로그인 -> LoggedInUser 를 통해 데이터 업데이트
    }
}
