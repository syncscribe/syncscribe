package io.syncscribe.common.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import io.syncscribe.common.contracts.User;

public class OAuthContext {
    public static User getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        var principal = authentication.getPrincipal();
        if (!(principal instanceof Jwt)) {
            return null;
        }
        var claims = ((Jwt)principal).getClaims();
        return new User(claims.get("sub").toString(), claims.get("email").toString(), claims.get("name").toString());
    }
}
