package dev.osterlund.spanners.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final ReactiveAuthenticationManager authenticationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint((s, e) -> Mono.fromCallable(() -> s.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)).then())
                .accessDeniedHandler((s, e) -> Mono.fromCallable(() -> s.getResponse().setStatusCode(HttpStatus.FORBIDDEN)).then())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/oauth2/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(server ->
                    server.jwt()
                            .authenticationManager(authenticationManager)
                )
                .build();
    }

}
