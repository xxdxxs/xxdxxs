package com.xxdxxs.support.conversion;

import com.xxdxxs.utils.ConvertUtil;
import com.xxdxxs.validation.Validation;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * instant转换
 * @author xxdxxs
 */
public class InstantConversion implements Conversion<Instant>{

    private enum Type { DATE, TIME, DATETIME }

    private static final Map<DateTimeFormatter, Type> FORMATTERS = new LinkedHashMap<>();

    /**定义支持的格式**/
    static {
        FORMATTERS.put(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), Type.DATETIME);
        FORMATTERS.put(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"), Type.DATETIME);
        FORMATTERS.put(DateTimeFormatter.ofPattern("HH:mm:ss"), Type.TIME);
        FORMATTERS.put(DateTimeFormatter.BASIC_ISO_DATE, Type.DATE);
        FORMATTERS.put(DateTimeFormatter.ISO_DATE, Type.DATE);
        FORMATTERS.put(DateTimeFormatter.ISO_DATE_TIME, Type.DATETIME);
        FORMATTERS.put(DateTimeFormatter.ISO_INSTANT, Type.DATETIME);
        FORMATTERS.put(DateTimeFormatter.ISO_LOCAL_DATE, Type.DATE);
        FORMATTERS.put(DateTimeFormatter.ISO_LOCAL_DATE_TIME, Type.DATETIME);
        FORMATTERS.put(DateTimeFormatter.ISO_OFFSET_DATE, Type.DATE);
        FORMATTERS.put(DateTimeFormatter.ISO_OFFSET_DATE_TIME, Type.DATETIME);
        FORMATTERS.put(DateTimeFormatter.ISO_ORDINAL_DATE, Type.DATE);
        FORMATTERS.put(DateTimeFormatter.ISO_WEEK_DATE, Type.DATE);
        FORMATTERS.put(DateTimeFormatter.ISO_ZONED_DATE_TIME, Type.DATETIME);
        FORMATTERS.put(DateTimeFormatter.RFC_1123_DATE_TIME, Type.DATETIME);
    };

    @Override
    public Optional<Instant> convert(Object value) {

        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }

        if (value instanceof Instant) {
            return Optional.of((Instant) value);
        }

        if (value instanceof LocalDateTime) {
            return convert((LocalDateTime) value);
        }

        if (value instanceof LocalDate) {
            return convert((LocalDate) value);
        }

        if (value instanceof LocalTime) {
            return convert((LocalTime) value);
        }

        if (value instanceof java.sql.Date) {
            return convert((java.sql.Date) value);
        }

        if (value instanceof java.sql.Time) {
            return convert((java.sql.Time) value);
        }

        if (value instanceof java.sql.Timestamp) {
            return convert((java.sql.Timestamp) value);
        }

        if (value instanceof Date) {
            return convert((Date) value);
        }

        if (value instanceof Calendar) {
            return convert((Calendar) value);
        }

        if (value instanceof OffsetDateTime) {
            return convert((OffsetDateTime) value);
        }

        if (Validation.isNumber(value)) {
            return convert(ConvertUtil.LONG.convert(value).orElse(0L).longValue());
        }
        return convert(String.valueOf(value));
    }

    public Optional<Instant> convert(LocalDateTime value) {
        return Optional.of(value.toInstant(ZoneOffset.UTC));
    }

    public Optional<Instant> convert(LocalDate value) {
        return convert(LocalDateTime.of(value, LocalTime.MIN));
    }

    public Optional<Instant> convert(LocalTime value) {
        return convert(value.atDate(LocalDate.ofEpochDay(0)));
    }

    public Optional<Instant> convert(Date value) {
        return Optional.of(value.toInstant());
    }

    public Optional<Instant> convert(java.sql.Date value) {
        return convert(value.getTime());
    }

    public Optional<Instant> convert(java.sql.Time value) {
        return convert(value.getTime());
    }

    public Optional<Instant> convert(java.sql.Timestamp value) {
        return convert(value.getTime());
    }

    public Optional<Instant> convert(Calendar value) {
        return Optional.of(value.toInstant());
    }

    public Optional<Instant> convert(OffsetDateTime value) {
        return Optional.of(value.toInstant());
    }

    public Optional<Instant> convert(long value) {
        try {
            return Optional.of(Instant.ofEpochMilli(value));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Instant> convert(String value) {
        try {
            return Optional.of(Instant.parse(value));
        } catch (DateTimeParseException e) {}

        for (Map.Entry<DateTimeFormatter, Type> entry : FORMATTERS.entrySet()) {
            try {
                switch (entry.getValue()) {
                    case DATE:
                        return convert(LocalDate.parse(value, entry.getKey()));
                    case DATETIME:
                        return convert(LocalDateTime.parse(value, entry.getKey()));
                    case TIME:
                        return convert(LocalTime.parse(value, entry.getKey()));
                }
            } catch (DateTimeParseException e) {}
        }

        return Optional.empty();
    }
}
