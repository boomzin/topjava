package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static List<MealTo> mealsWithExcess;

    @Override
    public void init() throws ServletException {
        super.init();
        mealsWithExcess = MealsUtil.filteredByStreams(MealsUtil.getMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getCaloriesPerDay());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals.jsp");
        request.setAttribute("mealsWithExcess", mealsWithExcess);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
