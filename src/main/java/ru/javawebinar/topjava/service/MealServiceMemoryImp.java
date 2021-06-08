package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealServiceMemoryImp implements MealService {
    private static final ConcurrentHashMap<Integer ,Meal> MEAL_REPOSITORY_MAP = new ConcurrentHashMap<>();

    private static final AtomicInteger MEAL_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(Meal meal) {
        final int mealId = MEAL_ID_HOLDER.incrementAndGet();
        meal.setId(mealId);
        MEAL_REPOSITORY_MAP.put(mealId, meal);
    }

    @Override
    public List<Meal> readAll() {
        return MEAL_REPOSITORY_MAP.values().parallelStream().collect(Collectors.toList());
    }

    @Override
    public Meal read(int id) {
        return MEAL_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(int id, Meal meal) {
        if (MEAL_REPOSITORY_MAP.containsKey(id)) {
            meal.setId(id);
            MEAL_REPOSITORY_MAP.put(id, meal);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return MEAL_REPOSITORY_MAP.remove(id) != null;
    }
}
