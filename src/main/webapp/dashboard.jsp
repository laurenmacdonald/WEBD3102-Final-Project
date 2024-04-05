<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-03-28
  Time: 11:18 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="categoryName" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <script src="https://cdn.plot.ly/plotly-2.30.0.min.js" charset="utf-8"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        <%@include file="/css/custom.css" %>
    </style>
</head>
<body>
<%--Side navigation --%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="/components/verticalNav.jsp"/>
        <div class="col-sm p-3 min-vh-100">
            <div class="container-md">
                <div class="d-flex justify-content-between pb-3"> <h1 class="fs-4">Hello, <c:out value="${sessionScope.user.firstName}"/></h1>
                </div>
                <div class="row row-cols-1 row-cols-md-3 g-4">
                    <!-- TOTAL BALANCE/INCOME/EXPENSE/GRAPH -->
                    <div class="col-lg-6 ">
                        <jsp:include page="/components/wealthSummary.jsp"/>
                    </div>
                    <!-- ACCOUNTS/TRANSACTIONS -->
                    <div class="col-lg-3">
                        <!-- Accounts section with carousel -->
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <h6>Accounts</h6>
                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-sm pb-3" data-bs-toggle="modal"
                                            data-bs-target="#accountModal">
                                        <i class="bi bi-plus-circle" aria-hidden="true"></i>&nbsp;&nbsp;Add Account
                                    </button>
                                </div>
                                <jsp:include page="/components/accountCarousel.jsp"/>
                            </div>
                        </div>
                        <div class="card mt-3">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <h6 class="card-title">Your Budgets</h6>
                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-sm pb-3" data-bs-toggle="modal"
                                            data-bs-target="#budgetModal">
                                        <i class="bi bi-plus-circle" aria-hidden="true"></i>&nbsp;&nbsp;Add New
                                    </button>
                                </div>
                                <jsp:include page="/components/budgetsComponent.jsp"/>
                                <a class="btn btn-sm pb-3" href="<%=request.getContextPath()%>/budgets"><i class="bi bi-arrow-up-right" aria-hidden="true"></i>&nbsp;&nbsp;See all</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <h6 class="card-title">Your Transactions</h6>
                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-sm pb-3" data-bs-toggle="modal"
                                            data-bs-target="#transactionModal">
                                        <i class="bi bi-plus-circle" aria-hidden="true"></i>&nbsp;&nbsp;Add New
                                    </button>
                                </div>
                                <jsp:include page="/components/transactionsComponent.jsp"/>
                                <a class="btn btn-sm pb-3" href="<%=request.getContextPath()%>/transactions"><i class="bi bi-arrow-up-right" aria-hidden="true"></i>&nbsp;&nbsp;See all</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- START MODAL - ADD ACCOUNT FORM -->
<div class="modal fade" id="accountModal" tabindex="-1" aria-labelledby="accountModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="accountModalLabel">Add Account</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <jsp:include page="/components/addAccountForm.jsp"/>
            </div>
        </div>
    </div>
</div>
<!-- END MODAL - ADD ACCOUNT FORM -->
<!-- START MODAL - ADD BUDGET FORM -->
<div class="modal fade" id="budgetModal" tabindex="-1" aria-labelledby="budgetModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="budgetModalLabel">Add Budget</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <jsp:include page="/components/addBudgetForm.jsp"/>
            </div>
        </div>
    </div>
</div>
<!-- END MODAL - ADD BUDGET FORM -->
<!-- START MODAL - ADD TRANSACTION FORM -->
<div class="modal fade" id="transactionModal" tabindex="-1" aria-labelledby="transactionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="transactionModalLabel">Add Transaction</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <jsp:include page="/components/addTransactionForm.jsp"/>
            </div>
        </div>
    </div>
</div>
<!-- END MODAL - ADD TRANSACTION FORM -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
