package io.syncscribe.common.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import io.syncscribe.common.contracts.User;

public class OAuthContext {
    private OAuthContext() {
    }

    public static User getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("Unauthorized");
        }
        var principal = authentication.getPrincipal();
        if (!(principal instanceof Jwt)) {
            throw new AccessDeniedException("Unauthorized");
        }
        var claims = ((Jwt)principal).getClaims();
        return new User(claims.get("sub").toString(), claims.get("email").toString(), claims.get("name").toString());
    }
}
