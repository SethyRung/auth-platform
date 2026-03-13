package com.sethy.service.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    @SuppressWarnings("unchecked")
    private Collection extractAuthorities(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess instanceof Map) {
            Map<String, Object> accessMap = (Map<String, Object>) realmAccess;
            Object roles = accessMap.get("roles");
            if (roles instanceof List) {
                List<String> roleList = (List<String>) roles;
                return roleList.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();
            }
        }

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess instanceof Map) {
            Map<String, Object> resourceMap = (Map<String, Object>) resourceAccess;
            Object account = resourceMap.get("account");
            if (account instanceof Map) {
                Map<String, Object> accountMap = (Map<String, Object>) account;
                Object accountRoles = accountMap.get("roles");
                if (accountRoles instanceof List) {
                    List<String> roleList = (List<String>) accountRoles;
                    return roleList.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .toList();
                }
            }
        }

        return Collections.emptyList();
    }
}
