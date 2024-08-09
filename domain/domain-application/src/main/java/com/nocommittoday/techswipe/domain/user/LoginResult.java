package com.nocommittoday.techswipe.domain.user;

public record LoginResult(
        UserId userId,
        AccessToken accessToken,
        RefreshToken refreshToken
) {
}
