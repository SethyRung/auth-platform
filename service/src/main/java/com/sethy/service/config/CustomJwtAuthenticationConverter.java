package com.sethy.service.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<SimpleGrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        LinkedHashSet<SimpleGrantedAuthority> authorities = new LinkedHashSet<>();

        Object realmAccessClaim = jwt.getClaim("realm_access");
        if (realmAccessClaim instanceof Map<?, ?> realmAccess) {
            authorities.addAll(extractRoles(realmAccess.get("roles")));
        }

        Object resourceAccessClaim = jwt.getClaim("resource_access");
        if (resourceAccessClaim instanceof Map<?, ?> resourceAccess) {
            for (Object clientClaim : resourceAccess.values()) {
                if (clientClaim instanceof Map<?, ?> clientAccess) {
                    authorities.addAll(extractRoles(clientAccess.get("roles")));
                }
            }
        }

        if (authorities.isEmpty()) {
            return Collections.emptyList();
        }

        return authorities;
    }

    private Collection<SimpleGrantedAuthority> extractRoles(Object rolesClaim) {
        if (!(rolesClaim instanceof Collection<?> roles)) {
            return Collections.emptyList();
        }

        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase(Locale.ROOT)))
                .toList();
    }
}
