<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h3><a href="meals?action=add">Add Meal</a></h3>
<style>
    .raz,
    .raz td {
        border-collapse: collapse;
        border: 2px solid black;
        padding: .3em 1em;
    }
</style>
<table class="raz">
    <tbody>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    <c:forEach var="meal" items="${requestScope.mealsWithExcess}">
        <tr style="color:${meal.isExcess() ? 'Red' : 'Green'}">
            <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/>">Delete</a></td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.getId()}"/>">Edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>