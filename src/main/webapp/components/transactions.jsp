<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-01
  Time: 2:07 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="categoryName" tagdir="/WEB-INF/tags" %>
<c:forEach var="transaction" items="${sessionScope.transactionList}" varStatus="loop">
    <div class="card border-light">
        <div class="card-body">
            <div class="card-title">
                <h6><categoryName:categoryNameTag
                        value="${transaction.categoryName}"/>&nbsp;<c:out
                        value="${transaction.categoryName}"/></h6>
            </div>
            <div class="d-flex justify-content-between">
                <p><small><fmt:formatDate type="date" value="${transaction.date}"/></small>
                </p>
                <c:choose>
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
                </c:choose>
            </div>
        </div>
    </div>
</c:forEach>