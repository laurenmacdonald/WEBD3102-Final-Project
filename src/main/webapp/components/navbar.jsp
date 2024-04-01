<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-03-28
  Time: 10:55 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="/css/custom.css" %>
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-light-green">
    <div class="container-fluid ps-5">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/home"><i class="bi bi-cash-stack" aria-hidden="true"
                                                                             style="font-size: 1.75rem;"></i>&nbsp;WealthWise</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">
                                <i class="bi bi-person-fill" aria-hidden="true"></i><span
                                    class="visually-hidden">Profile</span> Account
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/account">Account Details</a></li>
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/logout">Log Out</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/signup">Create Account</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/login">Login</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>