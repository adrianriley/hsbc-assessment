package com.hsbc.ratesapi.controller;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.service.EuroExchangeRateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class EuroExchangeRateController {
    private final EuroExchangeRateService euroExchangeRateService;

    public EuroExchangeRateController(EuroExchangeRateService euroExchangeRateService) {
        this.euroExchangeRateService = euroExchangeRateService;
    }

    /**
     * Get current EURO exchange rates.
     *
     * @return
     */
    @GetMapping("/rates/current")
    public String getCurrentRates(Model model) {
        model.addAttribute("rates", euroExchangeRateService.getCurrentExchangeRates());
        return "current";
    }

    /**
     * Get historical EURO exchange rates 6 months on the same day of the month as today.
     *
     * @return
     */
    @GetMapping("/rates/history")
    public String getRates(Model model) {
        CompoundExchangeRateReport rates = euroExchangeRateService.getHistoricalExchangeRates();
        model.addAttribute("rates", rates);
        model.addAttribute("currencies",
                rates.getDayRateReports().stream().flatMap(dayRateReport -> dayRateReport.getRates().keySet().stream())
                        .collect(Collectors.toCollection(() -> new TreeSet<>())));
        return "history";
    }
}
