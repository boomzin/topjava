<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="meals">Go back</a></h3>
<hr>
<c:set var="meal" value="${requestScope.meal}"/>
<c:set value="${meal eq null}" var="isNewMeal"/>
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" readonly="readonly" name="mealId"
           value="<c:out value="${isNewMeal ? '' : meal.id}" />"/> <br/>
    DateTime : <input
        type="datetime-local" name="dateTime"
        value="${meal.dateTime}"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.getDescription()}"/><br/>
    Calories : <input type="number" name="calories"
                      value="${meal.calories}"/><br/>
    <input type="submit" value="${isNewMeal ? 'Add meal' : 'Edit meal'}"/>
</form>
</body>