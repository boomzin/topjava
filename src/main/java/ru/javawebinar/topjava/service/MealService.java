package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

    void create(Meal meal);

    List<Meal> readAll();

    Meal read(int id);

    boolean update(int id, Meal meal);

    boolean delete(int id);

}
