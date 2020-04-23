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
    <h1>${rates.base} Exchange Rates</h1>
    <h2>${rates.date.format( DateTimeFormatter.ISO_LOCAL_DATE )}</h2>
    <table>
        <tr>
        <th>Currency</th>
        <th>Rate</th>
        </tr>
        <c:forEach var="rate" items="${rates.rates}">
        <tr><td><c:out value="${rate.key}"/></td><td><c:out value="${rate.value}"/></td>
        </c:forEach>
    </table>
    <p/>
    <button onclick="window.location.href='${historyUrl}'">Rate History</button>
</body>
</html>