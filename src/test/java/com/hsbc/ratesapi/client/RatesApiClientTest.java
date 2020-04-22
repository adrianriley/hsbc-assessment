package com.hsbc.ratesapi.client;

import com.hsbc.ratesapi.model.RateReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RatesApiClientTest {

    private final String currentRatesUrlTemplate = "https://api.ratesapi.io/api/latest?base={base}&symbols={symbols}";
    private final String pastRatesUrlTemplate = "https://api.ratesapi.io/api/{date}?base={base}&symbols={symbols}";
    @InjectMocks
    private RatesApiClient ratesApiClient;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ratesApiClient, "currentRatesUrlTemplate", currentRatesUrlTemplate);
        ReflectionTestUtils.setField(ratesApiClient, "pastRatesUrlTemplate", pastRatesUrlTemplate);
    }

    @Test
    void getExchangeRates_buildsUriVariables() {
        RateReport mockReport = mock(RateReport.class);
        given(restTemplate.getForObject(anyString(), any(), anyMap())).willReturn(mockReport);

        RateReport rateReport = ratesApiClient.getExchangeRates("EUR", LocalDate.of(2020, 4, 1), "GBP", "USD");

        assertThat(rateReport).isEqualTo(mockReport);
        verify(restTemplate).getForObject(eq(pastRatesUrlTemplate), eq(RateReport.class),
                eq(Map.of("date", "2020-04-01", "base", "EUR", "symbols", "GBP,USD")));
    }

    @Test
    void getCurrentExchangeRates_buildsUriVariables() {
        RateReport mockReport = mock(RateReport.class);
        given(restTemplate.getForObject(anyString(), any(), anyMap())).willReturn(mockReport);

        RateReport rateReport = ratesApiClient.getCurrentExchangeRates("EUR", "GBP", "USD");

        assertThat(rateReport).isEqualTo(mockReport);
        verify(restTemplate).getForObject(eq(currentRatesUrlTemplate), eq(RateReport.class),
                eq(Map.of("base", "EUR", "symbols", "GBP,USD")));
    }
}