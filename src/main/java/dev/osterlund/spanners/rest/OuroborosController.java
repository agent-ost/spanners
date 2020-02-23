package dev.osterlund.spanners.rest;

import dev.osterlund.spanners.rest.model.Instance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("instances/{instance_id}")
public class OuroborosController {

    @GetMapping
    public Mono<Instance> getInstance() {
        return Mono.just(Instance.builder().build());
    }

}
