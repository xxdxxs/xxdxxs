package com.xxdxxs.support.conversion;

import com.xxdxxs.utils.ConvertUtil;
import com.xxdxxs.utils.DateUtils;
import com.xxdxxs.validation.Validation;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

        if (value instanceof LocalDateTime) {
            return Optional.of(((LocalDateTime) value));
        }

        return ConvertUtil.INSTANT.convert(value).map(instant -> {
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        });
    }
}
