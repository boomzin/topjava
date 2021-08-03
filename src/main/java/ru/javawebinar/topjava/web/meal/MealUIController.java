package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/ui/meals/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController{

    @Override
    @GetMapping("/{id}")
    public Meal get(int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    @RequestMapping("/")
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam LocalDateTime dateTime,
                               @RequestParam String description,
                               @RequestParam int calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        if (id == 0) {
            super.create(meal);
        }
    }
    @Override
    @GetMapping(value = "/ui/meals/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam LocalDate startDate,
                                   @RequestParam LocalTime startTime,
                                   @RequestParam LocalDate endDate,
                                   @RequestParam LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
