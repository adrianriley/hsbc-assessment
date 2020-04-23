package com.hsbc.ratesapi.service;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.DayRateReport;
import com.hsbc.ratesapi.model.RateReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EuroExchangeRateServiceImplTest {
    @InjectMocks
    private EuroExchangeRateServiceImpl euroExchangeRateService;

    @Mock
    private ExchangeRateService exchangeRateService;

//    @Test
//    void getCurrentExchangeRates_callsExchangeRateServiceMethod() {
//        RateReport mockReport = mock(RateReport.class);
//        given(exchangeRateService.getCurrentExchangeRates(any(), any())).willReturn(mockReport);
//
//        RateReport exchangeRateReport = euroExchangeRateService.getCurrentExchangeRates();
//
//        then(exchangeRateReport).isEqualTo(mockReport);
//        verify(exchangeRateService).getCurrentExchangeRates(eq("EUR"), eq("GBP"), eq("USD"), eq("HKD"));
//    }
//
//
//    @Test
//    void getHistoricalExchangeRates_collectsDataFor6Months() {
//        given(exchangeRateService.getExchangeRates(any(), any(), any())).willAnswer(
//                new Answer<RateReport>() {
//                    private int i = 0;
//
//                    @Override
//                    public RateReport answer(InvocationOnMock invocation) throws Throwable {
//                        Map<String, BigDecimal> rates = Map.of(
//                                invocation.getArgument(2, String.class), new BigDecimal(++i),
//                                invocation.getArgument(3, String.class), new BigDecimal(++i),
//                                invocation.getArgument(4, String.class), new BigDecimal(++i));
//                        RateReport exchangeRateReport =
//                                new RateReport(invocation.getArgument(0, String.class),
//                                        invocation.getArgument(1, LocalDate.class),
//                                        rates);
//                        return exchangeRateReport;
//                    }
//                }
//        );
//
//        CompoundExchangeRateReport compoundExchangeRateReport = euroExchangeRateService.getHistoricalExchangeRates();
//
//        then(compoundExchangeRateReport.getBase()).as("base").isEqualTo("EUR");
//        then(compoundExchangeRateReport.getDayRateReports()).as("day reports").hasSize(6);
//        List<LocalDate> expectedDates = getExpectedDates();
//        then(compoundExchangeRateReport.getDayRateReports().stream()
//                .map(DayRateReport::getDate).collect(Collectors.toList()))
//                .containsExactlyElementsOf(expectedDates);
//        compoundExchangeRateReport.getDayRateReports().forEach(
//                exchangeRateDayReport -> then(exchangeRateDayReport.getRates().keySet()).containsExactly("GBP", "HKD",
//                        "USD"));
//    }
//
//    private List<LocalDate> getExpectedDates() {
//        LocalDate now = LocalDate.now();
//        List<LocalDate> expectedDates = new ArrayList<>();
//        int i = 6;
//        while (i > 0) {
//            expectedDates.add(now.minusMonths(--i));
//        }
//        return expectedDates;
//    }
//
}