package com.hsbc.ratesapi.client;

import com.hsbc.ratesapi.model.RateReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link RatesApiClient} using Spring {@link RestTemplate}.
 */
@Component
public class RatesApiClientImpl implements RatesApiClient {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final RestTemplate restTemplate;

    @Value("${ratesapi.past.rates.url}")
    private String pastRatesUrlTemplate;

    @Value("${ratesapi.current.rates.url}")
    private String currentRatesUrlTemplate;

    public RatesApiClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public RateReport getExchangeRates(String baseCurrencyCode, LocalDate effectiveDate, String... currencyCodes) {
        Map<String, String> uriVariables = Map.of(
                "date", DATE_FORMATTER.format(effectiveDate),
                "base", baseCurrencyCode,
                "symbols", joinCurrencyCodes(currencyCodes));
        return restTemplate.getForObject(pastRatesUrlTemplate, RateReport.class, uriVariables);
    }

    @Override
    public RateReport getCurrentExchangeRates(String baseCurrencyCode, String... currencyCodes) {
        Map<String, String> uriVariables = Map.of(
                "base", baseCurrencyCode,
                "symbols", joinCurrencyCodes(currencyCodes));
        return restTemplate.getForObject(
                currentRatesUrlTemplate, RateReport.class, uriVariables);
    }


    private String joinCurrencyCodes(String... currencyCodes) {
        return String.join(",", currencyCodes);
    }
}
