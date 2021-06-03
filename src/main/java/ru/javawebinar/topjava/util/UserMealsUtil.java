package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        mealsTo = filteredByOneLoop(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);


//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateWithExcess = new HashMap<>();
        for (UserMeal userMeal : meals) {
            dateWithExcess.put(userMeal.getLocalDate(), (dateWithExcess.getOrDefault(userMeal.getLocalDate(), 0)) + userMeal.getCalories());
        }
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getLocalTime(), startTime,endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), new AtomicBoolean(dateWithExcess.get(meal.getLocalDate()) > caloriesPerDay)));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateWithExcess = meals.stream()
                .collect(Collectors.toMap(
                        UserMeal::getLocalDate,
                        UserMeal::getCalories,
                        Integer::sum,
                        HashMap<LocalDate, Integer>::new
                ));

        return meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(), new AtomicBoolean(dateWithExcess.get(m.getLocalDate()) > caloriesPerDay)))
                .collect(Collectors.toList());
    }


    public static List<UserMealWithExcess> filteredByOneLoop(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, Integer> dateWithCalories = new HashMap<>();
        Map<LocalDate, AtomicBoolean> dateWithExcess = new HashMap<>();
        for (UserMeal meal : meals) {
            dateWithCalories.put(meal.getLocalDate(), dateWithCalories.getOrDefault(meal.getLocalDate(), 0) + meal.getCalories());
            dateWithExcess.putIfAbsent(meal.getLocalDate(),  new AtomicBoolean(false));
            if (dateWithCalories.get(meal.getLocalDate()) > caloriesPerDay) {
                dateWithExcess.get(meal.getLocalDate()).set(true);
            }
            if (TimeUtil.isBetweenHalfOpen(meal.getLocalTime(),startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), dateWithExcess.get(meal.getLocalDate())));
            }
        }

        return userMealWithExcesses;
    }
}
