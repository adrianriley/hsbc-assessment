package com.hsbc.ratesapi.client;

import com.hsbc.ratesapi.model.RateReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RatesApiClientIT.Config.class)
@TestPropertySource(properties = {
        "ratesapi.past.rates.url=https://api.ratesapi.io/api/{date}?base={base}&symbols={symbols}",
        "ratesapi.current.rates.url=https://api.ratesapi.io/api/latest?base={base}&symbols={symbols}"
})
public class RatesApiClientIT {
    @Autowired
    private RatesApiClient ratesApiClient;

    @Test
    void getExchangeRates() {

        LocalDate effectiveDate = LocalDate.of(2020, 4, 1);
        RateReport rateReport = ratesApiClient.getExchangeRates("EUR", effectiveDate, "GBP", "USD");

        assertThat(rateReport).isNotNull();
        assertThat(rateReport.getBase()).isEqualTo("EUR");
        assertThat(rateReport.getDate()).isEqualTo(effectiveDate);
        assertThat(rateReport.getRates().keySet()).containsExactlyInAnyOrder("GBP", "USD");
    }

    @Test
    void getCurrentExchangeRates() {

        RateReport rateReport = ratesApiClient.getCurrentExchangeRates("EUR", "GBP", "USD");

        assertThat(rateReport).isNotNull();
        assertThat(rateReport.getBase()).isEqualTo("EUR");
        // Can be today or yesterday, depending what time the test is run
        assertThat(rateReport.getDate()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(rateReport.getRates().keySet()).containsExactlyInAnyOrder("GBP", "USD");
    }

    @Configuration
    static class Config {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplateBuilder().build();
        }

        @Bean
        public RatesApiClient ratesApiClient() {
            return new RatesApiClient(restTemplate());
        }
    }
}
