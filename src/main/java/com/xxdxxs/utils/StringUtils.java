package com.xxdxxs.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author xxdxxs
 */
public class StringUtils extends org.springframework.util.StringUtils {

    public static boolean isEmpty(Object object) {
        return (object == null || object.toString().trim().length() == 0);
    }

    public final static Pattern NUMBERPATTERN = Pattern.compile("[^(0-9)]");

    public static void main(String[] args) {
        String a = null;
        String b = "";
        String c = "123";
        String d = "0989";
        System.out.println(isAllNotEmpty(a, d));
    }


    public static boolean isAllNotEmpty(Object... obj) {
        return Arrays.asList(obj).stream().allMatch(x -> !isEmpty(x));
    }

    /**
     * @param str 待校验字符串
     * @return 是否为中文
     * @Description 判断字符串中是否包含中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 用逗号分割转换list > String, 另外加上引号
     *
     * @param list
     * @return
     */
    public static String listToStringAddSign(List<String> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : list) {
            stringBuffer.append("'" + str + "',");
        }
        String str = stringBuffer.toString();
        return str.substring(0, str.length() - 1);
    }

    /**
     * 把用逗号连接的字符串加上引号
     *
     * @param str
     * @return
     */
    public static String stringAddSign(String str) {
        if (isEmpty(str)) {
            return "";
        } else if (!str.contains(",")) {
            return "'" + str + "'";
        }
        String[] arr = str.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (String temp : arr) {
            stringBuffer.append("'" + temp + "',");
        }
        str = stringBuffer.toString();
        return str.substring(0, str.length() - 1);
    }

    /**
     * 用逗号分割转换list > String
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : list) {
            stringBuffer.append(str + ",");
        }
        String str = stringBuffer.toString();
        return str.substring(0, str.length() - 1);
    }


    /**
     * 根据分隔符转换成list
     *
     * @param str
     * @param symbol
     * @return
     */
    public static List<String> stringToList(String str, String symbol) {
        if (!isAllNotEmpty(str, symbol)) {
            throw new IllegalArgumentException();
        }
        String[] arr = str.split(symbol);
        return Arrays.asList(arr);
    }

    /**
     * 保留字符串中的数字
     * @param str
     * @return
     */
    public static int retainNumber(String str) {
        return Integer.valueOf(NUMBERPATTERN.matcher(str).replaceAll("").trim());
    }
}
