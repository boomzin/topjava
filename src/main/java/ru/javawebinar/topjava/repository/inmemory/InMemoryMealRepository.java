package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final Map<Integer, Set<Integer>> mealsIdsByUserId = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Set<Integer> mealIdSet = new HashSet<>();
        if (!mealsIdsByUserId.containsKey(userId)) {
            mealsIdsByUserId.put(userId, mealIdSet);
        }
        mealIdSet.addAll(mealsIdsByUserId.get(userId));
        if (meal.isNew()) {
            log.info("create meal {} for user {}", meal, userId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            mealIdSet.add(meal.getId());
            mealsIdsByUserId.put(userId, mealIdSet);
            return meal;
        }
        if (!mealIdSet.contains(meal.getId())) {
            log.info("update: not found meal {} for user {}", meal, userId);
            return null;
        }
        log.info("update meal {} for user {}", meal, userId);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal {} for user {}", id, userId);
        if (mealsIdsByUserId.get(userId).remove(id)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (mealsIdsByUserId.get(userId).contains(id)) {
            log.info("get meal {} for user{}", id, userId);
            return repository.get(id);
        }
        log.info("get: not found meal {} for user {}", id, userId);
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll meal for user {}", userId);
        List<Meal> mealList = new ArrayList<>(repository.values().stream().filter(meal -> mealsIdsByUserId.get(userId).contains(meal.getId())).collect(Collectors.toList()));
        mealList.sort(Comparator.comparing(Meal::getDate).reversed());
        return mealList;
    }
}

