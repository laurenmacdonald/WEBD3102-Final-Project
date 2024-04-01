<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-03-28
  Time: 10:54 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        <%@include file="/css/custom.css" %>
    </style>
</head>
<body class="bg-body-light-green">
<jsp:include page="/components/navbar.jsp"/>
<% String signupSuccess = request.getParameter("signupSuccess"); %>
<% if (signupSuccess != null && !signupSuccess.isEmpty()) {%>
<div class="toast container position-fixed bottom-0 end-0 p-3 z-3 mb-5 show">
    <div class="toast-body d-flex justify-content-between align-items-center">
        <i class="bi bi-check-circle-fill" aria-hidden="true"></i>&nbsp;
        <strong class="me-auto">&nbsp;<%=signupSuccess%></strong>
        <button type="button" class="btn-close align-self-end" data-bs-dismiss="toast" aria-label="Close"></button>
    </div>
</div>
<% } %>
<div class="container col-md-5 min-vh-100">
    <div class="card m-4">
        <div class="card-body">
            <h1>Login</h1>
            <% String errorMessage = request.getParameter("error"); %>
            <% if (errorMessage != null && !errorMessage.isEmpty()) {%>
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>&nbsp;
                <%= errorMessage %>
            </div>
            <% } %>
            <form action="login/auth" method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="email" name="email" value="<c:out value='${email}'/>" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password"
                           value="<c:out value='${password}'/>" required>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>