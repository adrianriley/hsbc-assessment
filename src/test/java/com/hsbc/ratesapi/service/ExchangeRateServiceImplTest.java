package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.client.RatesApiClient;
import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.DayRateReport;
import com.hsbc.ratesapi.model.Rate;
import com.hsbc.ratesapi.model.RateReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {
    private static final String EURO = "EUR";
    private static final String GB_POUND = "GBP";
    private static final String US_DOLLAR = "USD";
    private static final String HONG_KONG_DOLLAR = "HKD";

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private RatesApiClient ratesApiClient;

    private Rate gbpRate;
    private Rate usdRate;
    private Rate hkdRate;

    @BeforeEach
    void setUp() {
        gbpRate = new Rate(GB_POUND, new BigDecimal("0.87343"));
        usdRate = new Rate(US_DOLLAR, new BigDecimal("1.086"));
        hkdRate = new Rate(HONG_KONG_DOLLAR, new BigDecimal("8.401"));
    }

    @Test
    void getCurrentExchangeRates_noRates_returnsReportWithNoRates() {
        givenNoCurrentExchangeRatesExist();

        RateReport report = getCurrentExchangeRates();

        thenReportHasNoRates(report, LocalDate.now());
    }

    @Test
    void getCurrentExchangeRates_rateAvailable_returnsReportWithRate() {
        givenGbpCurrentExchangeRateExists();

        RateReport report = getCurrentExchangeRates();

        thenReportIsPopulatedWithGbpRate(report, LocalDate.now());
    }

    @Test
    void getCurrentExchangeRates_ratesAvailable_returnsReportWithRates() {
        givenCurrentExchangeRatesExist();

        RateReport report = getCurrentExchangeRates();

        thenReportIsPopulatedWithRates(report, LocalDate.now());
    }

    @Test
    void getExchangeRates_noRates_returnsReportWithNoRates() {
        givenNoHistoricalExchangeRatesExist();

        CompoundExchangeRateReport compoundExchangeRateReport = getHistoricalExchangeRates();

        thenReportHasNoRates(compoundExchangeRateReport);
    }

    @Test
    void getHistoricalExchangeRates_collectsDataFor6Months() {
        givenHistoricalExchangeRatesExist();

        CompoundExchangeRateReport compoundExchangeRateReport = getHistoricalExchangeRates();

        thenReportIsPopulatedWithRates(compoundExchangeRateReport);
    }

    @Test
    void getCurrentExchangeRates_exceptionThrownByClient_throwsServiceException() {
        given(ratesApiClient.getCurrentExchangeRates(any(), any()))
                .willThrow(RestClientException.class);

        thenThrownBy(() -> exchangeRateService
                .getCurrentExchangeRates(EURO, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR))
                .isInstanceOf(ServiceException.class);
    }

    @Test
    void getHistoricalExchangeRates_exceptionThrownByClient_throwsServiceException() {
        given(ratesApiClient.getExchangeRates(any(), any(), any()))
                .willThrow(RestClientException.class);

        thenThrownBy(() -> exchangeRateService
                .getHistoricalExchangeRates(EURO, 6, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR))
                .isInstanceOf(ServiceException.class);
    }

    private List<LocalDate> getExpectedDates() {
        LocalDate now = LocalDate.now();
        List<LocalDate> expectedDates = new ArrayList<>();
        int i = 6;
        while (i > 0) {
            expectedDates.add(now.minusMonths(--i));
        }
        return expectedDates;
    }

    private CompoundExchangeRateReport getHistoricalExchangeRates() {
        return exchangeRateService.getHistoricalExchangeRates(EURO, 6, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR);
    }

    private RateReport getCurrentExchangeRates() {
        return exchangeRateService.getCurrentExchangeRates(EURO, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR);
    }

    private void givenNoCurrentExchangeRatesExist() {
        given(ratesApiClient.getCurrentExchangeRates(anyString(), any())).willReturn(
                new RateReport(EURO, LocalDate.now(), Collections.emptyMap()));
    }

    private void givenNoHistoricalExchangeRatesExist() {
        given(ratesApiClient.getExchangeRates(anyString(), any(), any())).willAnswer(
                invocation -> new RateReport(EURO, invocation.getArgument(1, LocalDate.class), Collections.emptyMap()));
    }

    private void givenGbpCurrentExchangeRateExists() {
        given(ratesApiClient.getCurrentExchangeRates(anyString(), any()))
                .willReturn(new RateReport(EURO, LocalDate.now(),
                        Collections.singletonMap(GB_POUND, gbpRate.getRate())));
    }

    private void givenCurrentExchangeRatesExist() {
        Map<String, BigDecimal> rates = new HashMap<>();
        Stream.of(gbpRate, usdRate, hkdRate)
                .forEach(rate -> rates.put(rate.getCurrencyCode(), rate.getRate()));
        given(ratesApiClient.getCurrentExchangeRates(anyString(), any())).willReturn(
                new RateReport(EURO, LocalDate.now(), rates));
    }

    private void givenHistoricalExchangeRatesExist() {
        Map<String, BigDecimal> rates = new HashMap<>();
        Stream.of(gbpRate, usdRate, hkdRate)
                .forEach(rate -> rates.put(rate.getCurrencyCode(), rate.getRate()));
        given(ratesApiClient.getExchangeRates(anyString(), any(), any())).willAnswer(
                invocation -> new RateReport(EURO, invocation.getArgument(1, LocalDate.class), rates));
    }

    private void thenReportHasNoRates(RateReport rateReport, LocalDate effectiveDate) {
        then(rateReport).isNotNull();
        then(rateReport.getBase()).as("base").isEqualTo(EURO);
        then(rateReport.getDate()).as("date").isEqualTo(effectiveDate);
        then(rateReport.getRates()).as("rates").isEmpty();
    }

    private void thenReportHasNoRates(CompoundExchangeRateReport report) {
        then(report).isNotNull();
        then(report.getBase()).as("base").isEqualTo(EURO);
        then(report.getDayRateReports()).as("dayRateReports").hasSize(6);
        report.getDayRateReports().forEach(
                dayRateReport -> then(dayRateReport.getRates()).as("rates for " + dayRateReport.getDate()).isEmpty());
    }

    private void thenReportIsPopulatedWithGbpRate(RateReport report,
                                                  LocalDate effectiveDate) {
        thenReportIsInitialised(report, effectiveDate);
        then(report.getRates()).as("rates").hasSize(1);
        then(report.getRates()).as("rates currencyCodes").containsOnlyKeys(gbpRate.getCurrencyCode());
        then(report.getRates()).as("rates values").containsValue(gbpRate.getRate());
    }

    private void thenReportIsPopulatedWithRates(RateReport report,
                                                LocalDate effectiveDate) {
        thenReportIsInitialised(report, effectiveDate);
        then(report.getRates()).as("rates").hasSize(3);
        then(report.getRates()).as("rates").containsExactlyInAnyOrderEntriesOf(
                Map.of(gbpRate.getCurrencyCode(), gbpRate.getRate(),
                        usdRate.getCurrencyCode(), usdRate.getRate(),
                        hkdRate.getCurrencyCode(), hkdRate.getRate()));
    }

    private void thenReportIsInitialised(RateReport report, LocalDate effectiveDate) {
        then(report).isNotNull();
        then(report.getBase()).as("base").isEqualTo(EURO);
        then(report.getDate()).as("date").isEqualTo(effectiveDate);
    }

    private void thenReportIsPopulatedWithRates(CompoundExchangeRateReport compoundExchangeRateReport) {
        then(compoundExchangeRateReport.getBase()).as("base").isEqualTo("EUR");
        then(compoundExchangeRateReport.getDayRateReports()).as("day reports").hasSize(6);
        List<LocalDate> expectedDates = getExpectedDates();
        then(compoundExchangeRateReport.getDayRateReports().stream()
                .map(DayRateReport::getDate).collect(Collectors.toList()))
                .containsExactlyElementsOf(expectedDates);
        compoundExchangeRateReport.getDayRateReports().forEach(
                exchangeRateDayReport -> then(exchangeRateDayReport.getRates().keySet()).containsExactly("GBP", "HKD",
                        "USD"));
    }
}