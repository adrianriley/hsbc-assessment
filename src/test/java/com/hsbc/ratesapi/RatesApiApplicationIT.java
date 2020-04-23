package com.hsbc.ratesapi;

import com.hsbc.ratesapi.controller.RatesDisplayController;
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
    private RatesDisplayController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void getCurrentRates() {
        String html =
                withBasicAuth().getForObject(
                        "http://localhost:" + port + "/rates-display/rates/EUR/current?currencies=GBP,HKD,USD",
                        String.class);
        assertThat(html).contains("EUR Exchange Rates");
        assertThat(html).containsPattern(
                "button  *onclick=.*/rates-display/rates/EUR/history\\?currencies=GBP,HKD,USD");
    }

    @Test
    void getRates() {
        String html =
                withBasicAuth().getForObject(
                        "http://localhost:" + port + "/rates-display/rates/EUR/history?currencies=GBP,HKD,USD",
                        String.class);
        assertThat(html).contains("EUR Exchange Rate History");
        assertThat(html).doesNotContain("<form");
    }

    @Test
    void getCurrentRates_unauthorized() {
        ResponseEntity responseEntity =
                restTemplate.getForEntity(
                        "http://localhost:" + port + "/rates-display/rates/EUR/current?currencies=GBP,HKD,USD",
                        String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getRates_unauthorized() {
        ResponseEntity responseEntity =
                restTemplate.getForEntity(
                        "http://localhost:" + port + "/rates-display/rates/EUR/history?currencies=GBP,HKD,USD",
                        String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private TestRestTemplate withBasicAuth() {
        return restTemplate.withBasicAuth("admin", "admin");
    }
}
