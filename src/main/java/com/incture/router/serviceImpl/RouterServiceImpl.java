package com.incture.router.serviceImpl;

import com.incture.router.service.RouterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class RouterServiceImpl implements RouterService {

    public static final String SCOPE = "scope";

    @Value("${redirect.user.url}")
    private String userURL;

    @Value("${redirect.admin.url}")
    private String adminURL;

    /**
     * @param jwt
     * @return
     */
    @Override
    public String getRedirectionUrl(Jwt jwt) {
        log.info("getRedirectionUrl started with jwt: {}", jwt.toString());

        log.info("admin URL: {}", adminURL);
        log.info("user URL: {}", userURL);

        log.info("Extracting Scopes from JWT");
        List<String> scopes = jwt.getClaimAsStringList(SCOPE);

        log.info("Scopes in jwt received:");
        scopes.forEach(System.out::println);
        log.info("------------");

        if (scopes.stream().anyMatch(s -> s.endsWith(".Admin"))) {
            return adminURL;
        } else if (scopes.stream().anyMatch(s -> s.endsWith(".User"))) {
            return userURL;
        }
        return "";
    }
}
