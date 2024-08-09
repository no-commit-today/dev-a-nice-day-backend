package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.UserSignupRequest;
import com.nocommittoday.techswipe.domain.user.UserSignupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSignupController {

    private final UserSignupService userSignUpService;

    public UserSignupController(UserSignupService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    @PostMapping("/api/user/v1/signup")
    public void signUp(@RequestBody @Validated UserSignupRequest request) {
        userSignUpService.signUp(request.accessToken());
    }
}
