package com.hsbc.ratesapi.controller;

import com.hsbc.ratesapi.model.CompoundExchangeRateReport;
import com.hsbc.ratesapi.service.ExchangeRateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rates-display")
public class RatesDisplayController {
    private final ExchangeRateService exchangeRateService;

    public RatesDisplayController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/rates/{baseCurrency}/current")
    public String getCurrentRates(@PathVariable String baseCurrency, @RequestParam String[] currencies, Model model) {
        model.addAttribute("rates", exchangeRateService.getCurrentExchangeRates(baseCurrency, currencies));
        return "current";
    }

    @GetMapping("/rates/{baseCurrency}/history")
    public String getRatesHistory(@PathVariable String baseCurrency, @RequestParam String[] currencies, Model model) {
//        CompoundExchangeRateReport rates =
//                exchangeRateService.getHistoricalExchangeRates(baseCurrency, currencies);
//        model.addAttribute("rates", rates);
//        model.addAttribute("currencies",
//                rates.getDayRateReports().stream().flatMap(dayRateReport -> dayRateReport.getRates().keySet().stream())
//                        .collect(Collectors.toCollection(TreeSet::new)));
//        return "history";
        model.addAttribute("rates", exchangeRateService.getHistoricalExchangeRates(baseCurrency, currencies));
        return "current";
    }
}
