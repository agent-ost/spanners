package dev.osterlund.spanners.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Set;

public class OuroborosConverter implements Converter<Jwt, Mono<OuroborosToken>> {

    @Override
    public Mono<OuroborosToken> convert(Jwt source) {
        return Mono.fromCallable(() -> new OuroborosToken(source, Set.of(), "Nisse Hult"));
    }
}
