package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceMemoryImp;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static List<MealTo> mealsWithExcess;
    private MealService mealService;
    private static String INSERT_OR_EDIT = "/mealsAddEdit";
    private static String LIST_MEALS = "/meals.jsp";

    @Override
    public void init() throws ServletException {
        super.init();
        this.mealService = new MealServiceMemoryImp();
        MealsUtil.initData(mealService);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet forward to meals.jsp");
        String forward;
        String action = request.getParameter("action") != null ? request.getParameter("action") : "null";

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealService.delete(mealId);
            forward = LIST_MEALS;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealService.read(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("add")) {
            forward = INSERT_OR_EDIT;
            request.setAttribute("meal", null);
        } else {
            forward = LIST_MEALS;
        }
        forward(request, response, forward);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.debug("doPost forward to meals.jsp");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        if ("".equals(request.getParameter("mealId"))) {
            mealService.create(meal);
        } else {
            int id = Integer.parseInt(request.getParameter("mealId"));
            mealService.update(id, meal);
        }
        forward(request, response, LIST_MEALS);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String listMeals) throws ServletException, IOException {
        mealsWithExcess = MealsUtil.filteredByStreams(mealService.readAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getCaloriesPerDay());
        Comparator<MealTo> comparator = (o1, o2) -> {
            if (o1 == null || o2 == null)
                return 0;
            return o1.getDateTime().compareTo(o2.getDateTime());
        };
        mealsWithExcess.sort(comparator);
        request.setAttribute("mealsWithExcess", mealsWithExcess);
        request.getRequestDispatcher(listMeals).forward(request, response);
    }
}
