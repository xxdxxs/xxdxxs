package com.xxdxxs.utils;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: xxdxxs
 * @Date: Created in 9:47 2019/4/3
 * @Description:
 */
public class CommonUtil {
    public static boolean isEmpty(Object object) {
        return (object == null || object.toString().trim().length() == 0);
    }


    /**
     *  用逗号分割转换list 》 String
     * @param list
     * @return
     */
    public static String listToString(List<String> list){
        StringBuffer stringBuffer = new StringBuffer();
        for(String str : list){
            stringBuffer.append(str + ",");
        }
        String str = stringBuffer.toString();
        return str.substring(0,str.length()-1);
    }


    /**
     *  用逗号分割转换list 》 String,另外加上引号成为查询条件
     * @param list
     * @return
     */
    public static String listToStringAddSign(List<String> list){
        if(list.isEmpty()){
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for(String str : list){
            stringBuffer.append("'" + str + "',");
        }
        String str = stringBuffer.toString();
        return str.substring(0,str.length()-1);
    }


    /**
     * 把用逗号连接的字符串加上引号成为查询条件
     * @param str
     * @return
     */
    public static String stringAddSign(String str){
        if(isEmpty(str)){
            return "";
        }else if(!str.contains(",")){
            return "'" + str + "'";
        }
        String[] arr = str.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for(String temp : arr){
            stringBuffer.append("'" + temp + "',");
        }
        str = stringBuffer.toString();
        return str.substring(0,str.length()-1);
    }


    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String getUTF8XMLString(String xml) {
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8) ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return xmlUTF8;
    }


    public static String transTimeStampToStr(Timestamp time){
        String str;
        try{
            str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        }catch (Exception e){
            return "";
        }
        return str;
    }


    public static String transTimeStampToStrByType(Timestamp time, String format){
        String str;
        try{
            str = new SimpleDateFormat(format).format(time);
        }catch (Exception e){
            return "";
        }
        return str;
    }

    public static Date transStrToDate(String timeStr, String format){
        Date date;
        try{
            date = new SimpleDateFormat(format).parse(timeStr);
        }catch (Exception e){
            return new Date();
        }
        return date;
    }

    public static String transStrByFormat(String timeStr, String format){
        String res;
        try{
            Date date = new SimpleDateFormat(format).parse(timeStr);
            res = new SimpleDateFormat(format).format(date);
        }catch (Exception e){
            return timeStr;
        }
        return res;
    }

    public static String getNowTimeStr(String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime date = LocalDateTime.now();
        String time = date.format(formatter);
        return time;
    }


    public static Date getNowTime(String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime date = LocalDateTime.now();
        String time = date.format(formatter);
        Date vdate = transStrToDate(time, format);
        return vdate;
    }


    public static String formatDate(Date date, String format){
        String str;
        try{
            str = new SimpleDateFormat(format).format(date);
        }catch (Exception e){
            return "";
        }
        return str;
    }

    /**
     * @Description 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
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
     * 获取该月最后一天
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay=0;
        //2月的平年瑞年天数
        if(month==2) {
            lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
        }else {
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 获取该月第一天
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获取当前月份
     * @return
     */
    public static int getCurrMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }


    public static String calTime(Date date, int num, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, num);
        date = cal.getTime();
        return sdf.format(date);

    }

    /**
     * 获取类中所有的属性名称
     */
    public static String[] getField(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        List<String> fieldList = new ArrayList<>();
        Arrays.stream(fields).forEach(field ->{
            fieldList.add(field.getName());
        });
        if(fieldList.contains("serialVersionUID")){
            fieldList.remove("serialVersionUID");
        }
        String[] arr = fieldList.toArray(new String[fieldList.size()]);
        return arr;
    }


    public static boolean isAllNotNull(Object... args){
        boolean flag = true;
        for(Object object : args ){
             if(StringUtils.isEmpty(object)){
                 flag = false;
                 break;
             }
        }
        return flag;
    }

}
