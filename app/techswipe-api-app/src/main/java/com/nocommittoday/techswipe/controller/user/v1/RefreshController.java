package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.RefreshRequest;
import com.nocommittoday.techswipe.controller.user.v1.response.LoginResponse;
import com.nocommittoday.techswipe.domain.user.LoginResult;
import com.nocommittoday.techswipe.domain.user.RefreshTokenDecoded;
import com.nocommittoday.techswipe.domain.user.UserRefreshService;
import com.nocommittoday.techswipe.infrastructure.user.JwtAccessTokenEncoder;
import com.nocommittoday.techswipe.infrastructure.user.JwtRefreshTokenDecoder;
import com.nocommittoday.techswipe.infrastructure.user.JwtRefreshTokenEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshController {

    private final UserRefreshService userRefreshService;
    private final JwtRefreshTokenDecoder jwtRefreshTokenDecoder;
    private final JwtAccessTokenEncoder jwtAccessTokenEncoder;
    private final JwtRefreshTokenEncoder jwtRefreshTokenEncoder;

    public RefreshController(
            UserRefreshService userRefreshService,
            JwtRefreshTokenDecoder jwtRefreshTokenDecoder,
            JwtAccessTokenEncoder jwtAccessTokenEncoder,
            JwtRefreshTokenEncoder jwtRefreshTokenEncoder
    ) {
        this.userRefreshService = userRefreshService;
        this.jwtRefreshTokenDecoder = jwtRefreshTokenDecoder;
        this.jwtAccessTokenEncoder = jwtAccessTokenEncoder;
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
    }

    @PostMapping("/api/user/v1/refresh")
    public LoginResponse refresh(@RequestBody @Validated RefreshRequest request) {
        RefreshTokenDecoded refreshTokenDecoded = jwtRefreshTokenDecoder.decode(request.refreshToken());
        LoginResult loginResult = userRefreshService.refresh(
                refreshTokenDecoded,
                jwtAccessTokenEncoder::encode,
                jwtRefreshTokenEncoder::encode
        );
        return LoginResponse.from(loginResult);
    }
}
