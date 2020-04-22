package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.DayRateReport;
import com.hsbc.ratesapi.model.RateReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.stream.IntStream;

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
        IntStream.range(0, 6)
                .forEach(i -> addExchangeRates(LocalDate.now().minusMonths(i), compoundExchangeRateReport));
        return compoundExchangeRateReport;
    }

    private void addExchangeRates(LocalDate effectiveDate, CompoundExchangeRateReport compoundExchangeRateReport) {
        RateReport rateReport = getExchangeRateReport(effectiveDate);
        DayRateReport dayRateReport =
                new DayRateReport(effectiveDate, new TreeMap<>(rateReport.getRates()));
        try {
            compoundExchangeRateReport.addDayRateReport(dayRateReport);
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Multiple results for date in server response", e);
        }
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
