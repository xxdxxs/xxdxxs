package com.xxdxxs.support.conversion;

import com.xxdxxs.service.ConditionFilterSupport;
import com.xxdxxs.validation.Validation;

import java.util.Optional;

public class LongConversion implements Conversion<Long> {

    @Override
    public Optional<Long> convert(Object value){
        if(Validation.isEmpty(value)){
            return Optional.empty();
        }
        if(Validation.isClass(value, long.class)){
            return convert((long) value);
        }
        if(value instanceof Long){
            return convert((Long)value);
        }

        return convert(String.valueOf(value));
    }

    public Optional<Long> convert(long value){
        return Optional.of(Long.valueOf(value));
    }

    public Optional<Long> convert(Long value){
        return Optional.of(value.longValue());
    }

    public Optional<Long> convert(String value){
        return Optional.of(Long.valueOf(value));
    }

}
