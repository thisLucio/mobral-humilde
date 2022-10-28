package com.solinftec.dev8api.intregation;

import com.solinftec.dev8api.repository.ApiUserRepository;
import com.solinftec.dev8api.resource.ApiUserResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class ApiUserResourceIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    private ApiUserResource apiUserResource;

    private String token;

    /*
    200 — Correcto.
    401 — Não autorizado.
    403 — Expirou.
    */

    @Test
    void testValidLogin() {
        HttpHeaders headers;
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));

        Map<String, String> map;
        map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "admin");

        HttpEntity<Map<String, String>> request;

        request = new HttpEntity<>(map, headers);

        ResponseEntity<String> tokenResponse;
        tokenResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/login", request, String.class);

        if (tokenResponse.getStatusCode() == HttpStatus.OK) {
            token = String.valueOf(tokenResponse.getHeaders().get(HttpHeaders.AUTHORIZATION));
        }
        Assertions.assertThat(tokenResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
