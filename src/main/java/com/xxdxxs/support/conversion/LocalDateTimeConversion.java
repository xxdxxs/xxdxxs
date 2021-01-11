package com.xxdxxs.support.conversion;

import com.xxdxxs.utils.DateUtils;
import com.xxdxxs.validation.Validation;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author xxdxxs
 */
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

    public Optional<LocalDateTime> convert(Date date) {
        LocalDateTime localDateTime = DateUtils.toLocalDateTime(date);
        return Optional.of(localDateTime);
    }

    public Optional<LocalDateTime> convert(String value) {
        LocalDateTime localDateTime = DateUtils.toLocalDateTime(value);
        return Optional.of(localDateTime);
    }
}
