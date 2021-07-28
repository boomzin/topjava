package ru.javawebinar.topjava.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class LocalDateAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(asList(new Class<?>[]{LocalDate.class}));
    }

    @Override
    public Printer<?> getPrinter(@Autowired LocalDateFormat annotation, Class<?> fieldType) {
        return null;
    }

    @Override
    public Parser<LocalDate> getParser(@Autowired LocalDateFormat annotation, Class<?> fieldType) {
        return (text, locale) -> parseLocalDate(text);
    }
}
