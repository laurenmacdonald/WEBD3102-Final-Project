<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-03
  Time: 9:59 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-sm-auto bg-white sticky-top">
    <div class="d-flex flex-sm-column flex-row flex-nowrap bg-white align-items-center sticky-top">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/home"><i class="bi bi-cash-stack" aria-hidden="true"
                                                                             style="font-size: 3rem;"></i>&nbsp;
        </a>
        <ul class="nav nav-pills nav-flush flex-sm-column flex-row flex-nowrap mb-auto mx-auto text-center align-items-center">
            <li>
                <div class="btn-group py-3 px-2">
                    <button class="btn btn-tertiary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi-calendar"></i> <fmt:formatDate pattern= "MMMM" value = "${sessionScope.startDate}" />
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="?month=1">January</a></li>
                        <li><a class="dropdown-item" href="?month=2">February</a></li>
                        <li><a class="dropdown-item" href="?month=3">March</a></li>
                        <li><a class="dropdown-item" href="?month=4">April</a></li>
                        <li><a class="dropdown-item" href="?month=5">May</a></li>
                        <li><a class="dropdown-item" href="?month=6">June</a></li>
                        <li><a class="dropdown-item" href="?month=7">July</a></li>
                        <li><a class="dropdown-item" href="?month=8">August</a></li>
                        <li><a class="dropdown-item" href="?month=9">September</a></li>
                        <li><a class="dropdown-item" href="?month=10">October</a></li>
                        <li><a class="dropdown-item" href="?month=11">November</a></li>
                        <li><a class="dropdown-item" href="?month=12">December</a></li>
                    </ul>
                </div>
            </li>
            <li>
                <a href="<%=request.getContextPath()%>/dashboard" class="nav-link py-3 px-2" title="Dashboard" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Dashboard">
                    <i class="bi-speedometer2 fs-3"></i>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath()%>/budgets" class="nav-link py-3 px-2" title="Budgets" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Budgets">
                    <i class="bi bi-wallet-fill fs-3"></i>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath()%>/transactions" class="nav-link py-3 px-2" title="Transactions" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Transactions">
                    <i class="bi-card-list fs-3"></i>
                </a>
            </li>
        </ul>
        <div class="dropdown">
            <a href="#" class="d-flex align-items-center justify-content-center p-3 nav-link text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi-person-circle h2"></i>
            </a>
            <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser3">
                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/account">Account Details</a></li>
                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/logout">Log Out</a></li>
            </ul>
        </div>
    </div>
</div>