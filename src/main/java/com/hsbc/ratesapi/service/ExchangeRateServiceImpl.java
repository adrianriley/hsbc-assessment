package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.client.RatesApiClient;
import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.DayRateReport;
import com.hsbc.ratesapi.model.RateReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.stream.IntStream;

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
     * Get current exchange rates for a given currency.
     *
     * @param fromCurrencyCode the base currency
     * @param toCurrencyCodes  the currency codes for which to get exchange rates.
     * @return a RateReport containing the requested exchange rates.
     */
    @Override
    public RateReport getCurrentExchangeRates(String fromCurrencyCode, String... toCurrencyCodes) {
        try {
            return ratesApiClient.getCurrentExchangeRates(fromCurrencyCode, toCurrencyCodes);
        } catch (Exception e) {
            throw new ServiceException("Failed to get exchange rates", e);
        }
    }

    /**
     * Get historical exchange rates for a given currency for the past 6 months,
     * for the same day of the month.
     *
     * @param fromCurrencyCode the base currency
     * @param numberOfMonths number of months for which history is required
     * @param toCurrencyCodes  the currency codes for which to get exchange rates
     * @return a CompoundExchangeRateReport containing the requested exchange rates
     */
    @Override
    public CompoundExchangeRateReport getHistoricalExchangeRates(String fromCurrencyCode, int numberOfMonths,
                                                                 String... toCurrencyCodes) {
        CompoundExchangeRateReport compoundExchangeRateReport = new CompoundExchangeRateReport(fromCurrencyCode);
        IntStream.range(0, numberOfMonths)
                .forEach(i -> addExchangeRates(compoundExchangeRateReport, fromCurrencyCode,
                        LocalDate.now().minusMonths(i), toCurrencyCodes));
        return compoundExchangeRateReport;
    }

    /**
     * Get exchange rates for the given date and add data to a CompoundExchangeRateReport
     *
     * @param compoundExchangeRateReport the report to add the results
     * @param fromCurrencyCode           the base currency
     * @param effectiveDate              the effective date
     * @param toCurrencyCodes            the currency codes for which to get exchange rates
     */
    private void addExchangeRates(CompoundExchangeRateReport compoundExchangeRateReport, String fromCurrencyCode,
                                  LocalDate effectiveDate,
                                  String... toCurrencyCodes) {
        try {
            compoundExchangeRateReport.addDayRateReport(
                    getDayRateReport(fromCurrencyCode, effectiveDate, toCurrencyCodes));
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Multiple results for date in server response", e);
        }
    }

    /**
     * Get exchange rates for a given currency on a given day and return the data in a DayRateReport.
     *
     * @param fromCurrencyCode the base currency
     * @param effectiveDate    the effective date
     * @param toCurrencyCodes  the currency codes for which to get exchange rates
     * @return a DayRateReport containing the requested exchange rates.
     */
    private DayRateReport getDayRateReport(String fromCurrencyCode, LocalDate effectiveDate, String[] toCurrencyCodes) {
        RateReport rateReport = getExchangeRates(fromCurrencyCode, effectiveDate, toCurrencyCodes);
        return new DayRateReport(effectiveDate, new TreeMap<>(rateReport.getRates()));
    }

    /**
     * Get exchange rates for a given currency on a given day.
     *
     * @param fromCurrencyCode the base currency
     * @param effectiveDate    the effective date
     * @param toCurrencyCodes  the currency codes for which to get exchange rates
     * @return a RateReport containing the requested exchange rates.
     * @throws ServiceException wrapping any Exception thrown by the RatesApiClient
     */
    private RateReport getExchangeRates(String fromCurrencyCode, LocalDate effectiveDate,
                                        String... toCurrencyCodes) {
        try {
            return ratesApiClient.getExchangeRates(fromCurrencyCode, effectiveDate, toCurrencyCodes);
        } catch (Exception e) {
            throw new ServiceException("Failed to get exchange rates", e);
        }
    }
}
