<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-01
  Time: 2:08 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="categoryName" tagdir="/WEB-INF/tags" %>
<c:forEach var="budget" items="${sessionScope.budgetList}" varStatus="loop">
    <div class="card border-light">
        <div class="card-body">
            <div class="card-title">
                <h6><categoryName:categoryNameTag
                        value="${budget.categoryName}"/>&nbsp;<c:out
                        value="${budget.categoryName}"/></h6>
            </div>
            <div class="d-flex justify-content-between">
                <p><small><fmt:formatNumber
                        value="${budget.actualAmount}" type="currency"/></small></p>
                <p><small><fmt:formatNumber
                        value="${budget.goalAmount}" type="currency"/></small></p>
            </div>
        </div>
    </div>
</c:forEach>
