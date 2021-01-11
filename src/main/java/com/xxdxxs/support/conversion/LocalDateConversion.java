package com.xxdxxs.support.conversion;

import com.xxdxxs.utils.DateUtils;
import com.xxdxxs.validation.Validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * @author xxdxxs
 */
public class LocalDateConversion implements Conversion<LocalDate> {

    @Override
    public Optional<LocalDate> convert(Object value) {
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }
        if (value instanceof Date) {
            return convert((Date) value);
        }
        if (value instanceof LocalDate) {
            return Optional.of((LocalDate) value);
        }
        if (value instanceof LocalDateTime) {
            return Optional.of(((LocalDateTime) value).toLocalDate());
        }
        return convert(String.valueOf(value));
    }

    public Optional<LocalDate> convert(Date date) {
        LocalDate localDate = DateUtils.toLocalDate(date);
        return Optional.of(localDate);
    }

    public Optional<LocalDate> convert(String value) {
        LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Optional.of(localDate);
    }

}
