package com.hsbc.ratesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RateReport extends DayRateReport {
    private String base;

    public RateReport() {
    }

    public RateReport(String base, LocalDate date, Map<String, BigDecimal> rates) {
        super(date, rates);
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

}
