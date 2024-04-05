<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-02
  Time: 3:23 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="categoryName" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transactions</title>
    <script src="https://cdn.plot.ly/plotly-2.30.0.min.js" charset="utf-8"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        <%@include file="/css/custom.css" %>
    </style>
</head>
<body>
<!-- START MODAL - ADD TRANSACTION FORM -->
<div class="modal fade" id="transactionModal2" tabindex="-1" aria-labelledby="transactionModalLabel"
     aria-hidden="true">
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
<!-- Display error message if transaction addition error exists -->
<% String transactionError = request.getParameter("transactionError"); %>
<% if (transactionError != null && !transactionError.isEmpty()) {%>
<div class="toast container position-fixed bottom-0 end-0 p-3 z-3 mb-5 show">
    <div class="toast-body d-flex justify-content-between align-items-center">
        <i class="bi bi-check-circle-fill" aria-hidden="true"></i>&nbsp;
        <strong class="me-auto">&nbsp;<%=transactionError%>
        </strong>
        <button type="button" class="btn-close align-self-end" data-bs-dismiss="toast"
                aria-label="Close"></button>
    </div>
</div>
<% } %>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="/components/verticalNav.jsp"/>
        <div class="col-sm p-3 min-vh-100">
            <div class="container-md">
                <div class="container text-left">
                    <h1 class="fs-4">Transactions from <fmt:formatDate pattern="MMMM"
                                                                       value="${sessionScope.startDate}"/></h1>
                    <table class="table table-sm table-bordered">
                        <thead>
                        <tr>
                            <th scope="col">Date</th>
                            <th scope="col">Category</th>
                            <th scope="col">Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="transaction" items="${sessionScope.transactionList}" varStatus="loop">
                            <tr>
                                <td><p><fmt:formatDate type="date" value="${transaction.date}"/></p></td>
                                <td><p><categoryName:categoryNameTag
                                        value="${transaction.categoryName}"/>&nbsp;<c:out
                                        value="${transaction.categoryName}"/></p></td>
                                <td><c:choose>
                                    <c:when test="${transaction.transactionType eq 'Income'}">
                                        <small class="d-inline-flex mb-3 px-2 py-1 fw-semibold text-success-emphasis bg-success-subtle border border-success-subtle rounded-2">
                                            +<fmt:formatNumber value="${transaction.amount}"
                                                               type="currency"/>
                                        </small>
                                    </c:when>
                                    <c:when test="${transaction.transactionType eq 'Expense'}">
                                        <small class="d-inline-flex mb-3 px-2 py-1 fw-semibold text-success-emphasis bg-danger-subtle border border-danger-subtle rounded-2">
                                            -<fmt:formatNumber value="${transaction.amount}"
                                                               type="currency"/>
                                        </small>
                                    </c:when>
                                </c:choose></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="3">
                                <button type="button" class="btn btn-sm pb-3" data-bs-toggle="modal"
                                        data-bs-target="#transactionModal2">
                                    <i class="bi bi-plus-circle" aria-hidden="true"></i>&nbsp;&nbsp;Add New
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
