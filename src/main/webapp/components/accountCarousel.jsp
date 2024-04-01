<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-01
  Time: 2:06 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="carouselExample" class="carousel slide">
    <div class="carousel-inner">
        <c:forEach var="account" items="${sessionScope.accountList}" varStatus="loop">
            <div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
                <div class="card text-bg-dark">
                    <img src="${sessionScope.backgroundImages[loop.index]}"
                         class="card-img" alt="...">
                    <div class="card-img-overlay">
                        <h5 class="card-title ms-3 mt-3"><c:out
                                value="${account.accountName}"/></h5>
                        <p class="card-text ms-3"><fmt:formatNumber
                                value="${account.balanceAfter}" type="currency"/><br><small>Created:
                            <fmt:formatDate type="date"
                                            value="${account.createdDate}"/></small>
                        </p>

                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button"
            data-bs-target="#carouselExample" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button"
            data-bs-target="#carouselExample" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
