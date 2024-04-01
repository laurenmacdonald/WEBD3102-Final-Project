<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-01
  Time: 2:09 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="add-account" method="post">
    <div class="mb-3">
        <label for="accountType" class="form-label">What type of account would you like to add?</label>
        <select class="form-select" aria-label="Account type select" id="accountType"
                name="accountType">
            <option selected></option>
            <option value="Chequing">Chequing</option>
            <option value="Savings">Savings</option>
            <option value="Credit Card">Credit Card</option>
            <option value="Line of Credit">Line of Credit</option>
        </select>
    </div>
    <div class="mb-3">
        <label for="createdDate" class="form-label">When did you open this account?</label>
        <input type="date" class="form-control" id="createdDate" name="createdDate"
               value="<c:out value='${createdDate}'/>" required>
    </div>
    <div class="mb-3">
        <label for="startingBalance" class="form-label">What is the balance?</label>
        <input type="text" class="form-control" id="startingBalance" name="startingBalance"
               value="<c:out value='${startingBalance}'/>" required>
    </div>
    <div class="mb-3 text-center">
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</form>
