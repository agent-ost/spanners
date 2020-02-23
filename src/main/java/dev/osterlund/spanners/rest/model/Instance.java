package dev.osterlund.spanners.rest.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Instance {
    String name;
    String id;
}
