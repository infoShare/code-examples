package infoshare.circuitbreaker.service;

import infoshare.circuitbreaker.model.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    private static final Map<String, User> USERS = Map.of("1", new User("1", "John"), "2", new User("2", "Jane"));

    @CircuitBreaker(name = "users")
    @TimeLimiter(name = "users")
    @Retry(name = "users")
    public Mono<User> getUserById(String userId) {
        return Mono.justOrEmpty(Optional.ofNullable(USERS.getOrDefault(userId, null)))
                .switchIfEmpty(Mono.error(() -> new WebClientResponseException(404, "User not found", null, null, null)))
                .onErrorMap(WebClientResponseException.class, this::handleException);
    }

    private Throwable handleException(Throwable ex) {
        return new RuntimeException("Failed to get user", ex);
    }

}
