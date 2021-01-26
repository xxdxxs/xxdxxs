package com.xxdxxs.support.conversion;

import com.xxdxxs.utils.ConvertUtil;
import com.xxdxxs.utils.DateUtils;
import com.xxdxxs.validation.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * LocalDate转换类
 * @author xxdxxs
 */
public class LocalDateConversion implements Conversion<LocalDate> {

    @Override
    public Optional<LocalDate> convert(Object value) {

        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }

        if (value instanceof LocalDate) {
            return Optional.of((LocalDate) value);
        }

        if (value instanceof LocalDateTime) {
            return Optional.of(((LocalDateTime) value).toLocalDate());
        }
        return ConvertUtil.INSTANT.convert(value).map(instant -> {
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        });
    }

}
