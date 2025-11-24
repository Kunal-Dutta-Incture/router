package com.incture.router.service;

import org.springframework.security.oauth2.jwt.Jwt;

public interface RouterService {
    String getRedirectionUrl(Jwt jwt);
}
