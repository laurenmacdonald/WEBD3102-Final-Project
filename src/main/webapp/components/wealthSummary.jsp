<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-01
  Time: 3:45 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Arrays" %>
<%
  // Setting up arrays for the JS Plot.ly graph
  // get the expense data from the session attribute
  String[] expenseDataKeys = (String[]) session.getAttribute("expenseDataKeys");
  Double[] expenseDataValues = (Double[]) session.getAttribute("expenseDataValues");

  // Convert Java arrays to JavaScript arrays
  String keysArrayString = Arrays.toString(expenseDataKeys);
  String valuesArrayString = Arrays.toString(expenseDataValues);

  // Properly escape JavaScript strings
  keysArrayString = keysArrayString.replace("[", "[\"").replace(", ", "\", \"").replace("]", "\"]");
  valuesArrayString = valuesArrayString.replace("[", "[").replace("]", "]");
%>
<div class="card mb-5">
  <div class="card-header">
    <div class="row">
      <div class="col">
        <p class="card-title">Your total balance</p>
        <h3 class="card-text"><fmt:formatNumber value="${sessionScope.totalWealth}"
                                                type="currency"/></h3>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <small>Earned this month</small>
        <br>
        <small><i class="bi bi-arrow-up" style="color: darkgreen;"></i><fmt:formatNumber
                value="${sessionScope.totalIncome}" type="currency"/></small>
      </div>
      <div class="col">
        <small>Spent this month</small>
        <br>
        <small><i class="bi bi-arrow-down" style="color: red;"></i><fmt:formatNumber
                value="${sessionScope.totalExpense}" type="currency"/></small>
      </div>
    </div>
  </div>
  <div class="card-body">
    <div id="transactionDonut" style="width:100%;max-width:inherit"></div>
  </div>
</div>
<script>
  const layout = {title:"Expense Summary",   colorway : ['#f3cec9', '#e7a4b6', '#cd7eaf', '#a262a9', '#6f4d96', '#3d3b72', '#182844']};
  const data = [{labels:<%= keysArrayString %>, values:<%= valuesArrayString %>, hole:.4, type:"pie"}];
  const config = {responsive: true}

  Plotly.newPlot("transactionDonut", data, layout, config,{displayModeBar: false});
</script>