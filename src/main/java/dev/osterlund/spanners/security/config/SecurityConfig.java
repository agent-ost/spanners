package dev.osterlund.spanners.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.SignedJWT;
import dev.osterlund.spanners.security.OuroborosConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final ResourceLoader resourceLoader;

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        var ret = new JwtReactiveAuthenticationManager(jwtDecoder());
        ret.setJwtAuthenticationConverter(ouroborosConverter());
        return ret;
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder
                .withJwkSource(staticFunc())
                .jwsAlgorithm(SignatureAlgorithm.ES256)
                .build();
    }

    @Bean
    public OuroborosConverter ouroborosConverter() {
        return new OuroborosConverter();
    }

    @SneakyThrows
    private Function<SignedJWT, Flux<JWK>> staticFunc() {
        var pubKey = resourceLoader.getResource("classpath:public.pem");
        var data = StreamUtils.copyToString(pubKey.getInputStream(), StandardCharsets.US_ASCII);
        var jwk = JWK.parseFromPEMEncodedObjects(data);
        return s -> {
            log.debug("{}",s);
            return Flux.just(jwk);
        };
    }

}
