package com.xxdxxs.support.conversion;

import com.xxdxxs.utils.ConvertUtil;
import com.xxdxxs.validation.Validation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * @author xxdxxs
 */
public class LocalTimeConversion implements Conversion<LocalTime> {

    @Override
    public Optional<LocalTime> convert(Object value) {
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }

        if (value instanceof LocalTime) {
            return Optional.of(((LocalTime) value));
        }

        if (value instanceof LocalDateTime) {
            return Optional.of(((LocalDateTime) value).toLocalTime());
        }

        return ConvertUtil.INSTANT.convert(value).map(instant -> {
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        });
    }
}
