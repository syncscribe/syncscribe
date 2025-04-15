package io.syncscribe.idpservice.endpoints;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @GetMapping("/debug-token")
    public Map<String, Object> getJwtClaims(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }
}
