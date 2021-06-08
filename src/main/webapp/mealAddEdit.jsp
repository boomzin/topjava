<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="meals">Go back</a></h3>
<hr>
<c:set var="meal" value="${requestScope.meal}"/>
<c:set value="${meal eq null}" var="isNewMeal"/>
<form method="POST" action='meals' name="frmAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${isNewMeal ? '' : meal.getId()}" />" /> <br />
    DateTime : <input
        type="text" name="dateTime"
        <fmt:parseDate value="${isNewMeal ? '2000-01-01 00:00' : meal.getDateTime().toString().replace('T', ' ')}" pattern="yyyy-MM-dd HH:mm" var="parseDateTime"/>
        value="<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${parseDateTime}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.getDescription()}" />" /> <br />
    Calories : <input type="text" name="calories"
                   value="<c:out value="${meal.getCalories()}" />" /> <br /> <input
        type="submit" value="${isNewMeal ? 'Add meal' : 'Edit meal'}" />
</form>
    </tbody>
</table>
</body>
</html>