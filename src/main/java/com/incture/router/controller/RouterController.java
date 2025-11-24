package com.incture.router.controller;

import com.incture.router.service.RouterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class RouterController {

    @Autowired
    RouterService routerService;

    @GetMapping("/route")
    public ResponseEntity<?> test(@AuthenticationPrincipal Jwt jwt) {
        if (jwt != null) {
            String redirectionURL = routerService.getRedirectionUrl(jwt);
            log.info("redirectionURL: {}", redirectionURL);

            if (!redirectionURL.isBlank()) {
                return ResponseEntity.status(302)
                        .header("Location", redirectionURL)
                        .build();
            } else {
                return ResponseEntity.status(403)
                        .body("Unauthorized: User does not have access to target url");
            }
        } else {
            log.info("JWT is null");
            return ResponseEntity.status(403)
                    .body("Unauthorized: Token not found in request");
        }

    }



    /*@GetMapping("/route")
    public ResponseEntity<?> routeUser(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("starting routeUser with jwt: " + jwt);

        List<String> scopes = jwt.getClaimAsStringList("scope");
        scopes.forEach(System.out::println);

        if (scopes == null) {
            return ResponseEntity.status(403).body("No scopes assigned in JWT");
        }

        if (scopes.stream().anyMatch(s -> s.endsWith(".Admin"))) {
            return ResponseEntity.status(302)
                    .header("Location", "https://httpbin.org/anything/admin")
                    .build();
        } else if (scopes.stream().anyMatch(s -> s.endsWith(".User"))) {
            return ResponseEntity.status(302)
                    .header("Location", "https://www.goole.com")
                    .build();
        } else {
            return ResponseEntity.status(403)
                    .body("Unauthorized: Role not found in token");
        }
    }*/
}
