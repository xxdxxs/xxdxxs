package com.xxdxxs.db.component;

import com.xxdxxs.annotate.handle.UniqueHandler;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * JDBC操作辅助类
 * @author xxdxxs
 */
public class JdbcHelper {


    /**
     * 数据库查询结果转换成指定类集合
     *
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return <T>
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
                        if (fd.getName().equalsIgnoreCase(resultSet.getMetaData().getColumnName(i))) {
                            fd.set(obj, resultSet.getObject(i));
                        }
                    }
                    list.add((T) obj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 判断是否有能确定唯一一条数据的字段
     *
     * @param object
     * @return boolean
     */
    public static boolean isHasUniqueColumn(Object object) {
        List<String> columns = new UniqueHandler().getUsedColumn(object.getClass());
        if (columns.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 返回实体类能确定唯一一条数据的字段
     *
     * @param object
     * @return List<String>
     */
    public static List<String> getUniqueColumn(Object object) {
        List<String> columns = new UniqueHandler().getUsedColumn(object.getClass());
        return columns;
    }


    /**
     * value不为空执行回调
     * @param key
     * @param value
     * @param biConsumer
     * @param <T>
     */
    public static <T> void ifPresent(String key, T value, BiConsumer<String, T> biConsumer) {
        if (!StringUtils.isEmpty(value)) {
            biConsumer.accept(key, value);
        }
    }

    /**
     * value不为空执行回调
     * @param value
     * @param consumer
     * @param <T>
     */
    public static <T> void ifPresent(T value, Consumer<T> consumer) {
        if (!StringUtils.isEmpty(value)) {
            consumer.accept(value);
        }
    }
}
