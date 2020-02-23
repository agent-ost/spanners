package dev.osterlund.spanners.rest;

import dev.osterlund.spanners.rest.model.AuthResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("oauth2")
public class AuthController {

    @PostMapping("token")
    public Mono<AuthResponse> processResponse() {
        return Mono.just(AuthResponse.builder().access_token("fake").build());
    }
}
