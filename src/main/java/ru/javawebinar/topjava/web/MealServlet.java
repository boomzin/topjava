package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MealMemoryStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String INSERT_OR_EDIT = "/mealAddEdit.jsp";
    private static String LIST_MEALS = "/meals.jsp";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private MealStorage mealStorage;

    @Override
    public void init() {
        this.mealStorage = new MealMemoryStorage();
        for (Meal meal : MealsUtil.meals) {
            mealStorage.create(meal);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug(String.format("doGet request - action: %s, mealId: %s", request.getParameter("action"), request.getParameter("mealId")));
        String action = request.getParameter("action") != null ? request.getParameter("action") : "null";
        int mealId;

        switch (action) {
            case "delete":
                mealId = Integer.parseInt(request.getParameter("mealId"));
                mealStorage.delete(mealId);
                response.sendRedirect("meals");
                return;
            case "edit":
                mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealStorage.read(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
                return;

            case "add":
                request.setAttribute("meal", null);
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
                return;
        }
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(mealStorage.readAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
        request.setAttribute("mealsWithExcess", mealsWithExcess);
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug(String.format("doPost request - mealId: %s, dateTime: %s, description: %s, calories: %s",
                request.getParameter("mealId"),
                request.getParameter("dateTime"),
                request.getParameter("description"),
                request.getParameter("calories")));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime").replace('T', ' '), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);

        if ("".equals(request.getParameter("mealId"))) {
            mealStorage.create(meal);
        } else {
            meal.setId(Integer.parseInt(request.getParameter("mealId")));
            mealStorage.update(meal);
        }
        response.sendRedirect("meals");

    }
}
