package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.client.RatesApiClient;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    private LocalDate effectiveDate;

    @BeforeEach
    void setUp() {
        effectiveDate = LocalDate.of(2020, 4, 1);
        gbpRate = new Rate(GB_POUND, new BigDecimal("0.87343"));
        usdRate = new Rate(US_DOLLAR, new BigDecimal("1.086"));
        hkdRate = new Rate(HONG_KONG_DOLLAR, new BigDecimal("8.401"));
    }

//    @Test
//    void getExchangeRates_noRates_returnsReportWithNoRates() {
//        givenNoExchangeRatesExist(effectiveDate);
//
//        RateReport exchangeRateReport = getExchangeRates();
//
//        thenExchangeRateReportHasNoRates(exchangeRateReport, effectiveDate);
//    }
//
//    @Test
//    void getExchangeRates_rateAvailable_returnsReportWithRate() {
//        givenGbpExchangeRateExists(effectiveDate);
//
//        RateReport exchangeRateReport = getExchangeRates();
//
//        thenExchangeRateReportIsPopulatedWithGbpRate(exchangeRateReport, effectiveDate);
//    }
//
//    @Test
//    void getExchangeRates_ratesAvailable_returnsReportWithRates() {
//        givenExchangeRatesExist(effectiveDate);
//
//        RateReport exchangeRateReport = getExchangeRates();
//
//        thenExchangeRateReportIsPopulatedWithRates(exchangeRateReport, effectiveDate);
//    }
//
//    @Test
//    void getCurrentExchangeRates_noRates_returnsReportWithNoRates() {
//        givenNoCurrentExchangeRatesExist();
//
//        RateReport exchangeRateReport = getCurrentExchangeRates();
//
//        thenExchangeRateReportHasNoRates(exchangeRateReport, LocalDate.now());
//    }
//
//    @Test
//    void getCurrentExchangeRates_rateAvailable_returnsReportWithRate() {
//        givenGbpCurrentExchangeRateExists();
//
//        RateReport exchangeRateReport = getCurrentExchangeRates();
//
//        thenExchangeRateReportIsPopulatedWithGbpRate(exchangeRateReport, LocalDate.now());
//    }
//
//    @Test
//    void getCurrentExchangeRates_ratesAvailable_returnsReportWithRates() {
//        givenCurrentExchangeRatesExist();
//
//        RateReport exchangeRateReport = getCurrentExchangeRates();
//
//        thenExchangeRateReportIsPopulatedWithRates(exchangeRateReport, LocalDate.now());
//    }
//
//    @Test
//    void getExchangeRates_exceptionThrownByClient_thorwsServiceException() {
//        given(ratesApiClient.getExchangeRates(any(), any(), any()))
//                .willThrow(RestClientException.class);
//
//        thenThrownBy(() -> exchangeRateService
//                .getExchangeRates(EURO, effectiveDate, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR))
//                .isInstanceOf(ServiceException.class);
//    }
//
//    @Test
//    void getCurrentExchangeRates_exceptionThrownByClient_thorwsServiceException() {
//        given(ratesApiClient.getCurrentExchangeRates(any(), any()))
//                .willThrow(RestClientException.class);
//
//        thenThrownBy(() -> exchangeRateService
//                .getCurrentExchangeRates(EURO, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR))
//                .isInstanceOf(ServiceException.class);
//    }
//
//    private RateReport getExchangeRates() {
//        return exchangeRateService.getExchangeRates(EURO, effectiveDate, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR);
//    }
//
//    private RateReport getCurrentExchangeRates() {
//        return exchangeRateService.getCurrentExchangeRates(EURO, GB_POUND, US_DOLLAR, HONG_KONG_DOLLAR);
//    }
//
//    private void givenNoCurrentExchangeRatesExist() {
//        given(ratesApiClient.getCurrentExchangeRates(anyString(), any())).willReturn(
//                new RateReport(EURO, LocalDate.now(), Collections.emptyMap()));
//    }
//
//    private void givenNoExchangeRatesExist(LocalDate effectiveDate) {
//        given(ratesApiClient.getExchangeRates(anyString(), any(), any())).willReturn(
//                new RateReport(EURO, effectiveDate, Collections.emptyMap()));
//    }
//
//    private void givenGbpCurrentExchangeRateExists() {
//        given(ratesApiClient.getCurrentExchangeRates(anyString(), any()))
//                .willReturn(new RateReport(EURO, LocalDate.now(),
//                        Collections.singletonMap(GB_POUND, gbpRate.getRate())));
//    }
//
//    private void givenGbpExchangeRateExists(LocalDate effectiveDate) {
//        given(ratesApiClient.getExchangeRates(anyString(), any(), any()))
//                .willReturn(new RateReport(EURO, effectiveDate,
//                        Collections.singletonMap(GB_POUND, gbpRate.getRate())));
//    }
//
//    private void givenCurrentExchangeRatesExist() {
//        Map<String, BigDecimal> rates = new HashMap<>();
//        Stream.of(gbpRate, usdRate, hkdRate)
//                .forEach(rate -> rates.put(rate.getCurrencyCode(), rate.getRate()));
//        given(ratesApiClient.getCurrentExchangeRates(anyString(), any())).willReturn(
//                new RateReport(EURO, LocalDate.now(), rates));
//    }
//
//    private void givenExchangeRatesExist(LocalDate effectiveDate) {
//        Map<String, BigDecimal> rates = new HashMap<>();
//        Stream.of(gbpRate, usdRate, hkdRate)
//                .forEach(rate -> rates.put(rate.getCurrencyCode(), rate.getRate()));
//        given(ratesApiClient.getExchangeRates(anyString(), any(), any())).willReturn(
//                new RateReport(EURO, effectiveDate, rates));
//    }
//
//    private void thenExchangeRateReportHasNoRates(RateReport exchangeRateReport, LocalDate effectiveDate) {
//        thenExchangeRateReportIsInitialised(exchangeRateReport, effectiveDate);
//        then(exchangeRateReport.getRates()).as("rates").isEmpty();
//    }
//
//    private void thenExchangeRateReportIsPopulatedWithGbpRate(RateReport exchangeRateReport,
//                                                              LocalDate effectiveDate) {
//        thenExchangeRateReportIsInitialised(exchangeRateReport, effectiveDate);
//        then(exchangeRateReport.getRates()).as("rates").hasSize(1);
//        then(exchangeRateReport.getRates()).as("rates currencyCodes").containsOnlyKeys(gbpRate.getCurrencyCode());
//        then(exchangeRateReport.getRates()).as("rates values").containsValue(gbpRate.getRate());
//    }
//
//    private void thenExchangeRateReportIsPopulatedWithRates(RateReport exchangeRateReport,
//                                                            LocalDate effectiveDate) {
//        thenExchangeRateReportIsInitialised(exchangeRateReport, effectiveDate);
//        then(exchangeRateReport.getRates()).as("rates").hasSize(3);
//        then(exchangeRateReport.getRates()).as("rates").containsExactlyInAnyOrderEntriesOf(
//                Map.of(gbpRate.getCurrencyCode(), gbpRate.getRate(),
//                        usdRate.getCurrencyCode(), usdRate.getRate(),
//                        hkdRate.getCurrencyCode(), hkdRate.getRate()));
//    }
//
//    private void thenExchangeRateReportIsInitialised(RateReport exchangeRateReport, LocalDate effectiveDate) {
//        then(exchangeRateReport).isNotNull();
//        then(exchangeRateReport.getBase()).as("base").isEqualTo(EURO);
//        then(exchangeRateReport.getDate()).as("date").isEqualTo(effectiveDate);
//    }
}