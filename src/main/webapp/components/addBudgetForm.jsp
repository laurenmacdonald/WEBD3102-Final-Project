<%--
  Created by IntelliJ IDEA.
  User: laure
  Date: 2024-04-01
  Time: 2:11 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="add-budget" method="post">
    <div class="mb-3">
        <label for="category" class="form-label">What would you like to budget for?</label>
        <select class="form-select" aria-label="Account type select" id="category"
                name="category">
            <option selected></option>
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
        </select>
    </div>
    <div class="mb-3">
        <label for="goalAmount" class="form-label">When is the goal amount for your budget?</label>
        <input type="number" class="form-control" id="goalAmount" name="goalAmount"
               value="<c:out value='${goalAmount}'/>" required>
    </div>
    <div class="mb-3">
        <label for="startDate" class="form-label">When do you want to start this budget?</label>
        <input type="date" class="form-control" id="startDate" name="startDate"
               value="<c:out value='${startDate}'/>" required>
    </div>
    <div class="mb-3">
        <label for="endDate" class="form-label">When do you want to end this budget?</label>
        <input type="date" class="form-control" id="endDate" name="endDate"
               value="<c:out value='${endDate}'/>" required>
    </div>
    <div class="mb-3 text-center">
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</form>
