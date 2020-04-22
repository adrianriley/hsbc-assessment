package com.hsbc.ratesapi;

import com.hsbc.ratesapi.controller.EuroExchangeRateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatesApiApplicationIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EuroExchangeRateController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void getCurrentRates() {
        String html =
                withBasicAuth().getForObject("http://localhost:" + port + "/rates/current", String.class);
        assertThat(html).contains("EUR Exchange Rates");
        assertThat(html).contains("value=\"Rate History\"");
    }

    @Test
    void getRates() {
        String html =
                withBasicAuth().getForObject("http://localhost:" + port + "/rates/history", String.class);
        assertThat(html).contains("EUR Exchange Rate History");
        assertThat(html).doesNotContain("<form");
    }

    @Test
    void getCurrentRates_unauthorized() {
        ResponseEntity responseEntity =
                restTemplate.getForEntity("http://localhost:" + port + "/rates/current", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getRates_unauthorized() {
        ResponseEntity responseEntity =
                restTemplate.getForEntity("http://localhost:" + port + "/rates/history", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private TestRestTemplate withBasicAuth() {
        return restTemplate.withBasicAuth("admin", "admin");
    }
}
