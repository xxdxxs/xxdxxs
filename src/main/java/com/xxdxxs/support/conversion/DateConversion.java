package com.xxdxxs.support.conversion;

import com.xxdxxs.utils.ConvertUtil;
import com.xxdxxs.validation.Validation;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class DateConversion implements Conversion<Date> {
    private static final String DEFAULT_DATAFORMAT = "yyyy-MM-dd HH:mm:ss";


    @Override
    public Optional<Date> convert(Object value) {
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }
        if (value instanceof Date) {
            return Optional.of((Date) value);
        }
        if (value instanceof LocalDate) {
            return convert((LocalDate) value);
        }
        if (value instanceof LocalDateTime) {
            return convert((LocalDateTime) value);
        }
        return convert(String.valueOf(value));
    }

    public Instant convertInstant(String value){
        return Instant.parse(value);
    }

    public Optional<Date> convert(String value){
        Instant instant = convertInstant(value);
        Date date = new Date(instant.toEpochMilli());
        return Optional.of(date);
    }

    public Optional<Date> convert(LocalDate value){
        ZonedDateTime zonedDateTime = value.atStartOfDay(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());
        return Optional.of(date);
    }

    public Optional<Date> convert(LocalDateTime value) {
        Date date = Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
        return Optional.of(date);
    }

    public Optional<String> convertByFormat(Object value){
        return convertByFormat(value, DEFAULT_DATAFORMAT);
    }

    public Optional<String> convertByFormat(Object value, String pattern){
        String time = null;
        if(value instanceof String){
            LocalDateTime localDateTime = ConvertUtil.LOCALDATETIME.convert(String.valueOf(value)).get();
            time = format(localDateTime, pattern);
        }
        if(value instanceof Date){
            LocalDateTime localDateTime = LocalDateTime.ofInstant(((Date) value).toInstant(), ZoneId.systemDefault());
            time = format(localDateTime, pattern);
        }
        if(value instanceof LocalDateTime){
            time = format((LocalDateTime) value, pattern);
        }
        if(value instanceof LocalDate){
            time = format((LocalDate) value, pattern);
        }
        return Optional.of(time);
    }


    public String format(LocalDateTime localDateTime, String pattern){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDateTime);
    }
    public String format(LocalDate localDate, String pattern){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDate);
    }
}
