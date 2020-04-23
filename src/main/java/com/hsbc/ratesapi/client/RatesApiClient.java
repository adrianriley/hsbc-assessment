package com.hsbc.ratesapi.client;

import com.hsbc.ratesapi.model.RateReport;

import java.time.LocalDate;

/**
 * Rest client for the exchange rate service at https://api.ratesapi.io.
 */
public interface RatesApiClient {
    /**
     * Get exchange rates for a currency against other currencies on the given date.
     *
     * @param baseCurrencyCode base currency code
     * @param effectiveDate    date
     * @param currencyCodes    currency codes to retrieve rates for
     * @return exchange rates on the given date
     */
    RateReport getExchangeRates(String baseCurrencyCode, LocalDate effectiveDate, String... currencyCodes);

    /**
     * Get current exchange rates for a currency against other currencies.
     *
     * @param baseCurrencyCode base currency code
     * @param currencyCodes    currency codes to retrieve rates for
     * @return current exchange rates
     */
    RateReport getCurrentExchangeRates(String baseCurrencyCode, String... currencyCodes);
}
