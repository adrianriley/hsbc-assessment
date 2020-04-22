package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.DayRateReport;
import com.hsbc.ratesapi.model.RateReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Exchange rate retrieval service for current and historical Euro exchange rates against GB pound, US dollar and
 * Hong Kong dollar.
 * Uses methods of {@link ExchangeRateService} with the required parameters to retrieve
 * exchange rates for Euro against GB pound, US dollar and Hong Kong dollar.
 */
@Service
public class EuroExchangeRateServiceImpl implements EuroExchangeRateService {
    private static final String EURO = "EUR";
    private static final String GB_POUND = "GBP";
    private static final String US_DOLLAR = "USD";
    private static final String HONG_KONG_DOLLAR = "HKD";
    private final ExchangeRateService exchangeRateService;

    public EuroExchangeRateServiceImpl(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Get current exchange rates for Euro against GB pound, US dollar and Hong Kong dollar.
     *
     * @return the exchange rates requested
     */
    @Override
    public RateReport getCurrentExchangeRates() {
        return exchangeRateService.getCurrentExchangeRates(EURO, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR);
    }

    /**
     * Get historical exchange rates for Euro against GB pound, US dollar and Hong Kong dollar for the past 6 months,
     * for the same day of the week.
     *
     * @return the exchange rates requested
     */
    @Override
    public CompoundExchangeRateReport getHistoricalExchangeRates() {
        CompoundExchangeRateReport compoundExchangeRateReport = new CompoundExchangeRateReport(EURO);
        for (int i = 0; i < 6; i++) {
            LocalDate effectiveDate = LocalDate.now().minusMonths(i);
            RateReport rateReport = getExchangeRateReport(effectiveDate);
            DayRateReport exchangeRateDayReport =
                    new DayRateReport(effectiveDate, new TreeMap<>(rateReport.getRates()));
            compoundExchangeRateReport.addDayRateReport(exchangeRateDayReport);
        }
        return compoundExchangeRateReport;
    }

    /**
     * Get exchange rates for Euro against GB pound, US dollar and Hong Kong dollar for the given date.
     *
     * @param effectiveDate date for which rates are required
     * @return the exchange rates requested
     */
    private RateReport getExchangeRateReport(LocalDate effectiveDate) {
        return exchangeRateService.getExchangeRates(EURO, effectiveDate, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR);
    }
}
