package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.RateReport;

/**
 * Service to retrieve exchange rates for Euro against GB pound, US dollar and Hong Kong dollar.
 */
public interface EuroExchangeRateService {

    /**
     * Get current exchange rates for Euro against GB pound, US dollar and Hong Kong dollar.
     *
     * @return the exchange rates requested
     */
    RateReport getCurrentExchangeRates();

    /**
     * Get historical exchange rates for Euro against GB pound, US dollar and Hong Kong dollar for the past 6 months,
     * for the same day of the week.
     *
     * @return the exchange rates requested
     */
    CompoundExchangeRateReport getHistoricalExchangeRates();
}
