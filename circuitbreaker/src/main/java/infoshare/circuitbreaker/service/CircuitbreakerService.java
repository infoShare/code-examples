package infoshare.circuitbreaker.service;

import infoshare.circuitbreaker.model.User;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.handler.advice.RequestHandlerCircuitBreakerAdvice.CircuitBreakerOpenException;
import org.springframework.retry.RetryException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class CircuitbreakerService {

    private final UserService userService;
    private final CircuitBreakerRegistry registry;

    public Mono<User> getByUserId(String userId) {
        return userService.getUserById(userId)
                .onErrorMap(RetryException.class, Throwable::getCause)
                .onErrorReturn(CircuitBreakerOpenException.class, new User(userId, userId))
                .doOnError(ex -> log.warn("getByUserId failed: {}", ex.toString()))
                .log();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
    }

}
