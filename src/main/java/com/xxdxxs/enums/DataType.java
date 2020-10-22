package com.xxdxxs.enums;

import com.xxdxxs.validation.Validation;

public enum DataType {

    STRING("string") {
        @Override
        public Boolean validate(Object object) {
           return Validation.isString(object);
        }
    },
    DOUBLE("double"){
        @Override
        public Boolean validate(Object object) {
            return Validation.isDouble(object);
        }
    },

    NUMBER("number") {
        @Override
        public Boolean validate(Object object) {
            return Validation.isNumber(object);
        }
    },
    INTEGER("integer") {
        @Override
        public Boolean validate(Object object) {
            return Validation.isIntegter(object);
        }
    },
    DATE("date") {
        @Override
        public Boolean validate(Object object) {
            return Validation.isDate(object);
        }
    },
    DATETIME("dateTime") {
        @Override
        public Boolean validate(Object object) {
            return Validation.isDateTime(object);
        }
    },
    LIST("list") {
        @Override
        public Boolean validate(Object object) {
            return Validation.isList(object);
        }
    },
    MAP("map") {
        @Override
        public Boolean validate(Object object) {
            return Validation.isMap(object);
        }
    },;

    private String code;

    DataType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DataType getDataType(String code) {
        for (DataType dataType : DataType.values()) {
            if (dataType.name().equalsIgnoreCase(code)) {
                return dataType;
            }
        }
        return null;
    }

    public abstract Boolean validate(Object data);


}
