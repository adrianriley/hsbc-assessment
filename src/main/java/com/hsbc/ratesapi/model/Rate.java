package com.hsbc.ratesapi.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Rate {
    private final String currencyCode;
    private final BigDecimal rate;

    public Rate(String currencyCode, BigDecimal rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rate that = (Rate) o;
        return Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCode, rate);
    }
}
