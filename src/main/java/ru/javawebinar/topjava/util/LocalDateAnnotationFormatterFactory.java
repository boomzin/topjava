package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.Set;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class LocalDateAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(LocalDate.class);
    }

    @Override
    public Printer<?> getPrinter(LocalDateFormat annotation, Class<?> fieldType) {
        return null;
    }

    @Override
    public Parser<LocalDate> getParser(LocalDateFormat annotation, Class<?> fieldType) {
        return (text, locale) -> parseLocalDate(text);
    }
}
