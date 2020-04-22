<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>${rates.base} Exchange Rates</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
    <h1>${rates.base} Exchange Rate History</h1>
    <table>
        <tr>
        <th>Currency</th>
        <c:forEach var="dayRateReport" items="${rates.dayRateReports}">
        <th><c:out value="${dayRateReport.date.format( DateTimeFormatter.ISO_LOCAL_DATE )}"/></th>
        </c:forEach>
        </tr>
        <c:forEach var="currency" items="${currencies}">
        <tr>
        <td><c:out value="${currency}"/></td>
            <c:forEach var="dayRateReport" items="${rates.dayRateReports}">
            <td><c:out value="${dayRateReport.rates.get(currency)}"/></td>
            </c:forEach>
        </tr>
        </c:forEach>
    </table>
</body>
</html>