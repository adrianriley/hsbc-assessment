package com.hsbc.ratesapi.controller;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.model.RateReport;
import com.hsbc.ratesapi.service.ExchangeRateService;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import javax.servlet.ServletContext;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RatesDisplayControllerTest {
    @InjectMocks
    private RatesDisplayController controller;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private Model model;

    @Mock
    private ServletContext servletContext;

    @Test
    void getCurrentRates() throws URISyntaxException {
        MockHttpServletRequest request =
                MockMvcRequestBuilders.get(new URI("http://host/rates-display/rates/EUR/current"))
                        .queryParam("currencies", "GBP,USD")
                        .buildRequest(servletContext);
        RateReport rateReport = mock(RateReport.class);
        given(exchangeRateService.getCurrentExchangeRates(any(), any())).willReturn(rateReport);

        String result = controller.getCurrentRates("EUR", new String[]{"GBP", "USD"}, request, model);

        then(result).isEqualTo("current");

        verify(exchangeRateService).getCurrentExchangeRates("EUR", "GBP", "USD");
        verify(model).addAttribute("rates", rateReport);
        verify(model).addAttribute("historyUrl",
                request.getRequestURL().append("?").append(request.getQueryString()).toString().replaceFirst("current",
                        "history"));
    }

    @Test
    void getRatesHistory() {
        CompoundExchangeRateReport compoundExchangeRateReport = mock(CompoundExchangeRateReport.class);
        given(exchangeRateService.getHistoricalExchangeRates(any(), any())).willReturn(compoundExchangeRateReport);

        String result = controller.getRatesHistory("EUR", new String[]{"GBP", "USD"}, model);

        then(result).isEqualTo("history");

        verify(exchangeRateService).getHistoricalExchangeRates("EUR", "GBP", "USD");
        verify(model).addAttribute("rates", compoundExchangeRateReport);

    }
}