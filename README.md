# hsbc-assessment
##Project for HSBC assessment

Implementation of web application to retrieve interest rates from service at https://api.ratesapi.io.
Endpoints exist to retrieve either current rates, or rates for the past six months on the same day.
The web aplication can retrieve rates for any currency.

To run the application:

`mvn spring-boot:run`

This runs the server on port 8080. To see current rates for the Euro against GB pound, US dollar and Hong-Kong dollar, as required,
open the URL

http://localhost:8080/rates-display/rates/EUR/current?currencies=GBP,USD,HKD

This page has a button which links to display the historic rates, or use the URL

http://localhost:8080/rates-display/rates/EUR/history?currencies=GBP,USD,HKD

For the RAML describing the service, open 

http://localhost:8080/raml/rates-display.raml

The application is secured with basic authentication. The only authorised username is `admin`,
password `admin`.