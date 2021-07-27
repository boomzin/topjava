package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';
    private final MatcherFactory<MealTo> mealToMatcherFactory = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class, "");

    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + meal4.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(meal4));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + meal4.getId()))
                .andExpect(status().isNoContent());
        MATCHER.assertMatch(mealService.getAll(UserTestData.USER_ID), meal7, meal6, meal5, meal3, meal2, meal1);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(mealToMatcherFactory.contentJson(getTos(meals, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)));

        Meal created = MATCHER.readFromJson(action);
        int newId = created.id();
        newMeal.setId(newId);
        MATCHER.assertMatch(newMeal, created);
        MATCHER.assertMatch(mealService.getAll(UserTestData.USER_ID), created, meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MATCHER.assertMatch(mealService.get(MEAL1_ID, UserTestData.USER_ID), updated);
    }

    @Test
    void getBetween() throws Exception {
        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 31), USER_ID);
        List<MealTo> mealsToFiltered = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(),
                LocalTime.of(12, 0), LocalTime.of(14, 0));
        perform(MockMvcRequestBuilders.get(REST_URL + "filtered?startDateTime=2020-01-30T12:00&endDateTime=2020-01-31T14:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(mealToMatcherFactory.contentJson(mealsToFiltered));
    }
}