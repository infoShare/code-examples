package infoshare.circuitbreaker.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CircuitbreakerServiceTest {

    @Autowired
    private CircuitbreakerService underTest;

    @Test
    void testSuccess() {
        underTest.getByUserId("1").subscribe(user -> {
            assertThat(user.userId()).isEqualTo("1");
            assertThat(user.name()).isEqualTo("John");
        });
    }

    @Test
    void testFailure() {
        StepVerifier.create(underTest.getByUserId("3"))
                .expectErrorMessage("Failed to get user")
                .verify();
        StepVerifier.create(underTest.getByUserId("3"))
                .expectErrorMessage("Failed to get user")
                .verify();
        StepVerifier.create(underTest.getByUserId("3"))
                .expectErrorMessage("Failed to get user")
                .verify();
    }

}