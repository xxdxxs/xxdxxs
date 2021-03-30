package com.xxdxxs.utils;

import com.xxdxxs.support.conversion.*;


/**
 * @author xxdxxs
 */
public class ConvertUtil {
    public static final StringConversion STRING = new StringConversion();
    public static final DoubleConversion DOUBLE = new DoubleConversion();
    public static final FloatConversion FLOAT = new FloatConversion();
    public static final LongConversion LONG = new LongConversion();
    public static final IntegerConversion INTEGER = new IntegerConversion();
    public static final DateConversion DATE = new DateConversion();
    public static final InstantConversion INSTANT = new InstantConversion();
    public static final LocalDateConversion LOCALDATE = new LocalDateConversion();
    public static final LocalDateTimeConversion LOCALDATETIME = new LocalDateTimeConversion();
    public static final LocalTimeConversion LOCALTIME = new LocalTimeConversion();

}
