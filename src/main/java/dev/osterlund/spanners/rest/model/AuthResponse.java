package dev.osterlund.spanners.rest.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AuthResponse {
    String access_token;
}
