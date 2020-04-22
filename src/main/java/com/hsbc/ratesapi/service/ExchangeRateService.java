package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.model.RateReport;

import java.time.LocalDate;

/**
 * Service to retrieve exchange rates, both current and historical.
 */
public interface ExchangeRateService {
    /**
     * Get exchange rates for a given currency on a given day.
     *
     * @param fromCurrencyCode the base currency
     * @param effectiveDate    the effective date
     * @param toCurrencyCodes  the currency codes for which to get exchange rates.
     * @return a {@link RateReport} containing the requested exchange rates.
     */
    RateReport getExchangeRates(String fromCurrencyCode, LocalDate effectiveDate,
                                String... toCurrencyCodes);

    /**
     * Get current exchange rates for a given currency.
     *
     * @param fromCurrencyCode the base currency
     * @param toCurrencyCodes  the currency codes for which to get exchange rates.
     * @return a {@link RateReport} containing the requested exchange rates.
     */
    RateReport getCurrentExchangeRates(String fromCurrencyCode, String... toCurrencyCodes);
}
