package io.syncscribe.idpservice.endpoints;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.syncscribe.common.contracts.User;
import io.syncscribe.common.auth.OAuthContext;

@RestController
@RequestMapping("/api/v1/users")
public class UserEndpoint {

    @GetMapping("/me")
    public User me(Principal principal) {
        return OAuthContext.getUser();
    }
}
