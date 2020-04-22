package com.hsbc.ratesapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for JSON read and write.
 */
class RateReportTest {
    @Test
    void readValue_jsonStringToJavaObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"base\":\"EUR\",\"rates\":{\"USD\":1.086,\"GBP\":0.87343},\"date\":\"2020-04-20\"}";

        RateReport rateReport = objectMapper.readValue(json, RateReport.class);

        assertThat(rateReport.getBase()).isEqualTo("EUR");
        assertThat(rateReport.getDate()).isEqualTo(LocalDate.of(2020, 4, 20));
        assertThat(rateReport.getRates().keySet()).containsExactlyInAnyOrder("GBP", "USD");
        assertThat(rateReport.getRates().get("GBP")).isEqualByComparingTo("0.87343");
        assertThat(rateReport.getRates().get("USD")).isEqualByComparingTo("1.086");
    }
}