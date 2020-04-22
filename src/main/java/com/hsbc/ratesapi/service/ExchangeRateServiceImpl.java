package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.client.RatesApiClient;
import com.hsbc.ratesapi.model.RateReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service to retrieve exchange rates, both current and historical.
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final RatesApiClient ratesApiClient;

    /**
     * Constructor.
     *
     * @param ratesApiClient client for rates service
     */
    public ExchangeRateServiceImpl(RatesApiClient ratesApiClient) {
        this.ratesApiClient = ratesApiClient;
    }

    /**
     * Get exchange rates for a given currency on a given day.
     *
     * @param fromCurrencyCode the base currency
     * @param effectiveDate    the effective date
     * @param toCurrencyCodes  the currency codes for which to get exchange rates.
     * @return a RateReport containing the requested exchange rates.
     */
    @Override
    public RateReport getExchangeRates(String fromCurrencyCode, LocalDate effectiveDate,
                                       String... toCurrencyCodes) {

        return ratesApiClient.getExchangeRates(fromCurrencyCode, effectiveDate, toCurrencyCodes);
    }

    /**
     * Get current exchange rates for a given currency.
     *
     * @param fromCurrencyCode the base currency
     * @param toCurrencyCodes  the currency codes for which to get exchange rates.
     * @return a RateReport containing the requested exchange rates.
     */
    @Override
    public RateReport getCurrentExchangeRates(String fromCurrencyCode, String... toCurrencyCodes) {
        return ratesApiClient.getCurrentExchangeRates(fromCurrencyCode, toCurrencyCodes);
    }
}
