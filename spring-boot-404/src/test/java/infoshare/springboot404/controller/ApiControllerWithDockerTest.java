package infoshare.springboot404.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Testcontainers
public class ApiControllerWithDockerTest {

    @Container
    public static DockerComposeContainer<?> serviceContainer =
            new DockerComposeContainer<>(new File("docker/docker-compose.yml"))
                    .withExposedService("service", 8080)
                    .withLogConsumer("service", new Slf4jLogConsumer(log).withSeparateOutputStreams())
                    .withLocalCompose(true);

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void executeMethod() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("key", "CORRECT");
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/api/method", HttpMethod.POST, new HttpEntity<>(headers), String.class);
        assertThat(exchange.getBody()).isEqualTo("No static resource api/method.");
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
