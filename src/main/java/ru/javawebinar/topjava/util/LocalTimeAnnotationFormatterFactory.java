package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.Set;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class LocalTimeAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(LocalTime.class);
    }

    @Override
    public Printer<?> getPrinter(LocalTimeFormat annotation, Class<?> fieldType) {
        return null;
    }

    @Override
    public Parser<?> getParser(LocalTimeFormat annotation, Class<?> fieldType) {
        return (text, locale) -> parseLocalTime(text);
    }
}
