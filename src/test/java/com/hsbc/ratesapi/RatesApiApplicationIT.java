package com.hsbc.ratesapi;

import com.hsbc.ratesapi.controller.EuroExchangeRateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatesApiApplicationIT {

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
                restTemplate.getForObject("http://localhost:" + port + "/rates/current", String.class);
        assertThat(html).contains("EUR Exchange Rates");
        assertThat(html).contains("value=\"Rate History\"");
    }

    @Test
    void getRates() {
        String html =
                restTemplate.getForObject("http://localhost:" + port + "/rates/history", String.class);
        assertThat(html).contains("EUR Exchange Rate History");
        assertThat(html).doesNotContain("<form");
    }
}
