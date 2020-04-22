package com.hsbc.ratesapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Historical exchange rates for a currency against other currencies on multiple days.
 */
public class CompoundExchangeRateReport {

    /**
     * Base currency code
     */
    private final String base;

    private final Map<LocalDate, DayRateReport> dayRateReports = new TreeMap<>();

    /**
     * Required for JSON deserialization, factory method to create instance.
     *
     * @param base base currency
     * @return
     */
    @JsonCreator
    public static CompoundExchangeRateReport create(@JsonProperty("base") String base) {
        return new CompoundExchangeRateReport(base);
    }

    public CompoundExchangeRateReport(@JsonProperty("base") String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    /**
     * Get the collection of exchange rates with other currencies, in date order
     *
     * @return collection of exchange rates
     */
    public Collection<DayRateReport> getDayRateReports() {
        return Collections.unmodifiableCollection(dayRateReports.values());
    }

    public void setDayRateReports(Collection<DayRateReport> dayRateReports) {
        dayRateReports.forEach(this::addDayRateReport);
    }

    /**
     * Add a new exchange rate day report. Only one exchange rate for any particular date is allowed.
     *
     * @param dayRateReport exchange rate report to add
     * @throws IllegalArgumentException if the collection of rates already contains a {@link DayRateReport} for the same
     *                                  date.
     */
    public void addDayRateReport(DayRateReport dayRateReport) {
        if (dayRateReports.containsKey(dayRateReport.getDate())) {
            throw new IllegalArgumentException(DateTimeFormatter.ISO_LOCAL_DATE.format(dayRateReport.getDate()));
        }
        this.dayRateReports.put(dayRateReport.getDate(), dayRateReport);
    }
}
