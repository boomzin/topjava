package ru.javawebinar.topjava.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class LocalTimeAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(asList(new Class<?>[]{LocalTime.class}));
    }

    @Override
    public Printer<?> getPrinter(@Autowired LocalTimeFormat annotation, Class<?> fieldType) {
        return null;
    }

    @Override
    public Parser<?> getParser(@Autowired LocalTimeFormat annotation, Class<?> fieldType) {
        return (text, locale) -> parseLocalTime(text);
    }
}
