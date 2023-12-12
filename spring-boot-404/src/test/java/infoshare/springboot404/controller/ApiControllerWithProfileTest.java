package infoshare.springboot404.controller;

import infoshare.springboot404.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("profile")
@Import(GlobalExceptionHandler.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerWithProfileTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void executeMethod() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("key", "CORRECT");
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:" + port + "/api/method", HttpMethod.POST, new HttpEntity<>(headers), String.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(exchange.getBody()).isEqualTo("No static resource api/method.");
    }
}