package com.xxdxxs.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Validation {
    public static final Pattern REGEX_MOBILE = Pattern.compile("^1\\d{10}$");
    public static final Pattern REGEX_TELEPHONE = Pattern.compile("^[0][0-9]{2,3}-[0-9]{7,8}$");
    public static final Pattern REGEX_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    public static final Pattern REGEX_URL = Pattern.compile("^(http|www|ftp)://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$", 2);
    public static final Pattern REGEX_IP_ADDR = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");


    public Validation() {
    }

    public static boolean isPresent(Object value) {
        return value != null;
    }

    public static boolean isEmpty(Object value) {
        if (!isPresent(value)) {
            return true;
        } else if (value instanceof String) {
            return String.valueOf(value).trim().equals("");
        } else if (value instanceof CharSequence) {
            return ((CharSequence)value).length() == 0;
        } else if (value.getClass().isArray()) {
            return Array.getLength(value) == 0;
        } else if (value instanceof Collection) {
            return ((Collection)value).isEmpty();
        } else if (value instanceof Map) {
            return ((Map)value).isEmpty();
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    public static boolean isClass(Object value, Class<?> clazz) {
        return value != null && clazz != null ? clazz.isInstance(value) : false;
    }

    public static <T> boolean isClass(Class<?> value, Class<?> clazz) {
        return value != null && clazz != null ? clazz.isAssignableFrom(value) : false;
    }

    public static boolean isCollection(Object value) {
        return isClass(value, Collection.class);
    }

    public static boolean isList(Object value){
        return isClass(value, List.class);
    }

    public static boolean isMap(Object value) {
        return isClass(value, Map.class);
    }

    public static boolean isNumber(Object value) {
        return isNumber(value, Number.class);
    }

    public static <T extends Number> boolean isNumber(Object value, Class<T> clazz) {
        return isNumber(value, clazz, (Predicate)null);
    }

    public static <T extends Number> boolean isNumber(Object value, Class<T> clazz, Predicate<Number> callback) {
        try {
            T number = NumberUtils.parseNumber(String.valueOf(value), clazz);
            return callback == null || callback.test(number);
        } catch (IllegalArgumentException var4) {
            return false;
        }
    }

    public static boolean isString(Object value) {
        return value instanceof String;
    }

    public static boolean isIntegter(Object value) {
        return value instanceof Integer;
    }

    public static boolean isDouble(Object value) {
        return value instanceof Double;
    }

    public static boolean isBoolean(Object value) {
        if (value == null) {
            return false;
        } else if (value instanceof Boolean) {
            return true;
        } else if (!(value instanceof Number)) {
            return false;
        } else {
            return value.equals(1) || value.equals(0) || value.equals(1L) || value.equals(0L);
        }
    }

    public static boolean isIp(Object value) {
        return match(value, REGEX_IP_ADDR);
    }

    public static boolean isEmail(Object value) {
        return match(value, REGEX_EMAIL);
    }

    public static boolean isMobile(Object value) {
        return match(value, REGEX_MOBILE);
    }

    public static boolean isTelephone(Object value) {
        return match(value, REGEX_TELEPHONE);
    }

    public static boolean isUrl(Object value) {
        return match(value, REGEX_URL);
    }

    public static boolean match(Object value, String regex) {
        return isPresent(value) && isString(value) && !StringUtils.isEmpty(regex) ? match(value, Pattern.compile(regex)) : false;
    }

    public static boolean match(Object value, Pattern pattern) {
        return isPresent(value) && isString(value) && pattern != null ? pattern.matcher(String.valueOf(value)).matches() : false;
    }

    public static boolean isDate(Object value) {
        return value instanceof LocalDate ? true : isDate(value, "yyyy-MM-dd");
    }

    public static boolean isDateTime(Object value) {
        return value instanceof LocalDateTime ? true : isDate(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static boolean isTime(Object value) {
        return value instanceof LocalTime ? true : isDate(value, "HH:mm:ss");
    }

    public static boolean isDate(Object value, String format) {
        if (!isPresent(value)) {
            return false;
        } else if (Date.class.isAssignableFrom(value.getClass())) {
            return true;
        } else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                formatter.setLenient(true);
                formatter.parse(String.valueOf(value));
                return true;
            } catch (ParseException var3) {
                return false;
            }
        }
    }

    public static boolean isJSON(Object value) {
        try {
            if(!isPresent(value) || !isString(value)){
                return false;
            }
            return new ObjectMapper().readTree(String.valueOf(value)) != null;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

}
