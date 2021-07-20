package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService mealService;

    @PostMapping("/meals")
    public String updateCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            log.info("update meal {} for user {} from JspMealController updateCreate method", meal.getId(), userId);
            mealService.update(meal, userId);
        } else {
            mealService.create(meal, userId);
            log.info("create new meal for user {} from JspMealController updateCreate method", userId);
        }
        return "redirect:meals";
    }

    @GetMapping("/meals/delete")
    public String Delete(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        int id = Integer.valueOf(request.getParameter("id"));
        log.info("delete meal {} for user {} from JspMealController", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/meals/update")
    public String update(HttpServletRequest request, Model model) throws ServletException, IOException {
        int userId = SecurityUtil.authUserId();
        int id = Integer.valueOf(request.getParameter("id"));
        log.info("update meal {} for user {} from JspMealController update method", id, userId);
        Meal meal = mealService.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals/add")
    public String add(Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(LocalDateTime.parse(DateTimeUtil.toString(LocalDateTime.now()), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), null, 10);
        model.addAttribute("meal", meal);
        log.info("add meal for user {} from JspMealController add method", userId);
        return "mealForm";
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {} from JspMealController", userId);
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/meals/filter")
    public String getFilteredMeals(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("get filtered meals for user {} from JspMealController", userId);
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        List<Meal> filteredByDate = mealService.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        model.addAttribute("meals", MealsUtil.getFilteredTos(filteredByDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }
}
