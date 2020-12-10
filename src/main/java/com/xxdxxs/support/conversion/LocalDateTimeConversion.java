package com.xxdxxs.support.conversion;

import com.xxdxxs.validation.Validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class LocalDateTimeConversion implements Conversion<LocalDateTime> {
    @Override
    public Optional<LocalDateTime> convert(Object value) {
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }
        if (value instanceof Date) {
            return convert((Date) value);
        }
        if (value instanceof LocalDateTime) {
            return Optional.of((LocalDateTime) value);
        }
        return convert(String.valueOf(value));
    }

    public Optional<LocalDateTime> convert(Date date){
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime();
        return Optional.of(localDateTime);
    }

    public Optional<LocalDateTime> convert(String value){
        LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Optional.of(localDateTime);
    }
}
