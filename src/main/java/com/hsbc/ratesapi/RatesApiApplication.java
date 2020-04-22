package com.hsbc.ratesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * RatesAPI spring boot application.
 */
@SpringBootApplication(scanBasePackages = {"com.hsbc"})
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class RatesApiApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RatesApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RatesApiApplication.class);
    }
}