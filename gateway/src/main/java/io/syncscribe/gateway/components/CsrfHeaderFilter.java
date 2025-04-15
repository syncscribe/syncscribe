package io.syncscribe.gateway.components;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CsrfHeaderFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var token = (Mono<CsrfToken>) exchange.getAttribute(CsrfToken.class.getName());
        if(token != null) {
            return token.flatMap(t -> chain.filter(exchange));
        }
        return chain.filter(exchange);
    }
}
