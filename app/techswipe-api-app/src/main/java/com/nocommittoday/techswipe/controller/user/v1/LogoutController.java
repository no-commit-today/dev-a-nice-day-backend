package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.LogoutRequest;
import com.nocommittoday.techswipe.domain.user.UserLogoutService;
import com.nocommittoday.techswipe.infrastructure.user.JwtRefreshTokenDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    private final UserLogoutService userLogoutService;
    private final JwtRefreshTokenDecoder jwtRefreshTokenDecoder;

    public LogoutController(UserLogoutService userLogoutService, JwtRefreshTokenDecoder jwtRefreshTokenDecoder) {
        this.userLogoutService = userLogoutService;
        this.jwtRefreshTokenDecoder = jwtRefreshTokenDecoder;
    }

    @PostMapping("/api/user/v1/logout")
    public void logout(LogoutRequest logoutRequest) {
        userLogoutService.logout(jwtRefreshTokenDecoder.decode(logoutRequest.refreshToken()));
    }
}
