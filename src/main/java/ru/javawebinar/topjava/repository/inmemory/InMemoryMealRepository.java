package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final Map<Integer, Set<Integer>> mealsIdsByUserId = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Set<Integer> mealIdSet = new HashSet<>();
        mealIdSet.addAll(mealsIdsByUserId.get(userId));
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            mealIdSet.add(meal.getId());
            mealsIdsByUserId.put(userId, mealIdSet);
            return meal;
        }
        mealIdSet.add(meal.getId());
        mealsIdsByUserId.put(userId, mealIdSet);
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (mealsIdsByUserId.get(userId).remove(id)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (mealsIdsByUserId.get(userId).contains(id)) {
            return repository.get(id);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> mealList = new ArrayList<>(repository.values().stream().filter(meal -> mealsIdsByUserId.get(userId).contains(meal)).collect(Collectors.toList()));
        mealList.sort(Comparator.comparing(Meal::getDate).reversed());
        return mealList;
    }
}

