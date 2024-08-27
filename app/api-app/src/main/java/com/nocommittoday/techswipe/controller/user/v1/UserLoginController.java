package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.LoginRequest;
import com.nocommittoday.techswipe.controller.user.v1.response.LoginResponse;
import com.nocommittoday.techswipe.domain.user.LoginResult;
import com.nocommittoday.techswipe.domain.user.UserLoginService;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2AccessToken;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtAccessTokenEncoder;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtRefreshTokenEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {

    private final UserLoginService userLoginService;
    private final JwtAccessTokenEncoder jwtAccessTokenEncoder;
    private final JwtRefreshTokenEncoder jwtRefreshTokenEncoder;

    public UserLoginController(
            UserLoginService userLoginService,
            JwtAccessTokenEncoder jwtAccessTokenEncoder,
            JwtRefreshTokenEncoder jwtRefreshTokenEncoder
    ) {
        this.userLoginService = userLoginService;
        this.jwtAccessTokenEncoder = jwtAccessTokenEncoder;
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
    }

    @PostMapping("/api/user/v1/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        LoginResult loginResult = userLoginService.login(
                new OAuth2AccessToken(OAuth2Provider.GITHUB, request.accessToken()),
                jwtAccessTokenEncoder::encode,
                jwtRefreshTokenEncoder::encode
        );
        return LoginResponse.from(loginResult);
    }
}
