package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryStorage implements MealStorage {
    private final Map<Integer, Meal> mealRepositoryMap = new ConcurrentHashMap<>();

    private final AtomicInteger mealIdHolder = new AtomicInteger();

    @Override
    public Meal create(Meal meal) {
        final int mealId = mealIdHolder.incrementAndGet();
        meal.setId(mealId);
        mealRepositoryMap.put(mealId, meal);
        return meal;
    }

    @Override
    public List<Meal> readAll() {
        return new ArrayList<>(mealRepositoryMap.values());
    }

    @Override
    public Meal read(int id) {
        return mealRepositoryMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        return mealRepositoryMap.put(meal.getId(), meal) == null ? null : meal;
    }

    @Override
    public boolean delete(int id) {
        return mealRepositoryMap.remove(id) != null;
    }
}
