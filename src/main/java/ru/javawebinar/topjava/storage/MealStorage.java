package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {

    Meal create(Meal meal);

    List<Meal> readAll();

    Meal read(int id);

    Meal update(Meal meal);

    boolean delete(int id);
}
