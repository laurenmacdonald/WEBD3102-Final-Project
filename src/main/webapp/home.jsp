<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-03-28
  Time: 10:57 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="featureIcon" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>WealthWise</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        <%@include file="/css/custom.css" %>
    </style>
</head>
<body>
<jsp:include page="/components/navbar.jsp"/>
<!-- Redirect to dashboard if logged in (session user attribute exists), otherwise show landing page -->
<c:choose>
    <c:when test="${!empty sessionScope.user}">
        <c:redirect url="/dashboard"/>
    </c:when>
    <c:otherwise>
        <!-- HOME PAGE -->
        <section class="section-bg" style="background-image: url('./images/homeBackground.png'); background-size: cover;">
            <!-- Display log out success message if user was redirected from logging out -->
            <% String logoutSuccess = request.getParameter("logoutSuccess"); %>
            <% if (logoutSuccess != null && !logoutSuccess.isEmpty()) {%>
            <div class="toast container position-fixed bottom-0 end-0 p-3 z-3 mb-5 show">
                <div class="toast-body d-flex justify-content-between align-items-center">
                    <i class="bi bi-check-circle-fill" aria-hidden="true"></i>&nbsp;
                    <strong class="me-auto">&nbsp;<%=logoutSuccess%>
                    </strong>
                    <button type="button" class="btn-close align-self-end" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
            <% } %>
            <!-- MAIN HOME PAGE CONTENT -->
            <div class="d-flex justify-content-center align-items-center h-100">
                <div class="container-md">
                    <div class="text text-center p-5 m-5">
                        <h1 style="font-weight: bold;"><i class="bi bi-cash-stack" aria-hidden="true" style="font-size: calc(2rem + 1.5vw);"></i>&nbsp;WealthWise. </h1>
                        <h1>It will help you manage your wealth and make you richer.</h1>
                        <div class="container mt-3 pt-5">
                            <div class="row row-cols-1 row-cols-md-3 g-2">
                                <div class="col">
                                    <small class="d-inline-flex mb-3 px-2 py-1 fw-semibold text-success-emphasis bg-body-light-green border-success-subtle rounded-2">
                                        <featureIcon:featureIconTag value="receipt"/>&nbsp;Track income and expenses.</small>
                                </div>
                                <div class="col">
                                    <small class="d-inline-flex mb-3 px-2 py-1 fw-semibold text-success-emphasis bg-body-light-green border-success-subtle rounded-2">
                                        <featureIcon:featureIconTag value="piggy-bank"/>&nbsp;Budget planning.</small>
                                </div>
                                <div class="col">
                                    <small class="d-inline-flex mb-3 px-2 py-1 fw-semibold text-success-emphasis bg-body-light-green border-success-subtle rounded-2">
                                        <featureIcon:featureIconTag value="cash-stack"/>&nbsp;Grow wealth.</small>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <a class="btn btn-primary btn-lg m-3 px-3 fw-semibold rounded-4" href="<%=request.getContextPath()%>/signup" role="button">Try For Free</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </c:otherwise>
</c:choose>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>