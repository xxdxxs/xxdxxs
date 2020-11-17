package com.xxdxxs.db.component;

import com.xxdxxs.utils.CommonUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper {

    /**
     * 数据库查询结果转换成指定类集合
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> resultSetToList(ResultSet resultSet, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        Object obj = null;
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    obj = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field fd : fields) {
                        fd.setAccessible(true);
                        System.out.println(fd.getName());
                        if (fd.getName().equalsIgnoreCase(resultSet.getMetaData().getColumnName(i))) {
                            fd.set(obj, resultSet.getObject(i));
                        }
                    }
                    list.add((T) obj);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
