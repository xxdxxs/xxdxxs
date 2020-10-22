package com.xxdxxs.utils;




import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author: xxdxxs
 * @Date: Created in 9:47 2020/9/18
 * @Description:
 */
public class TimeUtil {

    /**
     * 获取该月最后一天
     * @param month
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

    public static void main(String[] args) {
        Object object = null;
        System.out.println(">>>>>>");
    }
}
