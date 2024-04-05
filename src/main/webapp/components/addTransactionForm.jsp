<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-01
  Time: 2:11 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="add-transaction" method="post">
    <div class="mb-3">
        <label for="accountID" class="form-label">What account?</label>
        <select class="form-select" aria-label="Account type select" id="accountID"
                name="accountID">
            <option selected disabled></option>
            <c:forEach var="account" items="${sessionScope.accountList}" varStatus="loop">
                <option value="${account.accountID}"><c:out value="${account.accountName}"/></option>
            </c:forEach>
<%--            <option value="1">Chequing</option>--%>
<%--            <option value="2">Savings</option>--%>
<%--            <option value="3">Line of Credit</option>--%>
<%--            <option value="4">Credit Card</option>--%>
        </select>
    </div>
    <div class="mb-3">
        <label for="category" class="form-label">What was the transaction?</label>
        <select class="form-select" aria-label="Account type select" id="category"
                name="category">
            <option selected disabled></option>
            <option value="1">Rent/Mortgage</option>
            <option value="2">Internet</option>
            <option value="3">Phone</option>
            <option value="4">Water</option>
            <option value="5">Electricity</option>
            <option value="6">Oil</option>
            <option value="7">Transportation</option>
            <option value="8">Groceries</option>
            <option value="9">Safety Fund</option>
            <option value="10">Vacation</option>
            <option value="11">Health and Wellness</option>
            <option value="12">Restaurant</option>
            <option value="13">Bar</option>
            <option value="14">Coffee</option>
            <option value="15" >Pay Cheque</option>
            <option value="16" >Tax Refund</option>
            <option value="17" >Gift</option>
        </select>
    </div>
    <div class="mb-3">
        <label for="amount" class="form-label">How much was the transaction?</label>
        <input type="text" class="form-control" id="amount" name="amount"
               value="<c:out value='${amount}'/>" required>
    </div>
    <div class="mb-3">
        <label for="date" class="form-label">What day was the transaction?</label>
        <input type="date" class="form-control" id="date" name="date"
               value="<c:out value='${date}'/>" required>
    </div>
    <div class="mb-3">
        <label for="budgetLink" class="form-label">Do you want to link this budget to a transaction of the same category?</label>
        <select class="form-select" aria-label="Link to a budget" id="budgetLink"
                name="budgetLink">
            <option selected disabled></option>
            <option value="yes">Yes</option>
            <option value="no">No</option>
        </select>
    </div>
    <div class="mb-3 text-center">
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</form>

