package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.RateReport;

/**
 * Service to retrieve exchange rates, both current and historical.
 */
public interface ExchangeRateService {

    /**
     * Get current exchange rates for a given currency.
     *
     * @param fromCurrencyCode the base currency
     * @param toCurrencyCodes  the currency codes for which to get exchange rates
     * @return a {@link RateReport} containing the requested exchange rates
     */
    RateReport getCurrentExchangeRates(String fromCurrencyCode, String... toCurrencyCodes);

    /**
     * Get historical exchange rates for a given currency for the past 6 months,
     * for the same day of the month.
     *
     * @param fromCurrencyCode the base currency
     * @param numberOfMonths number of months for which history is required
     * @param toCurrencyCodes  the currency codes for which to get exchange rates
     * @return a {@link CompoundExchangeRateReport} containing the requested exchange rates
     */
    CompoundExchangeRateReport getHistoricalExchangeRates(String fromCurrencyCode, int numberOfMonths,
                                                          String... toCurrencyCodes);
}
