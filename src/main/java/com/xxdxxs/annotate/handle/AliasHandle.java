package com.xxdxxs.annotate.handle;

import com.xxdxxs.annotate.Alias;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * entity属性别名注解(用于赋值属性的)
 * @author xxdxxs
 */
public class AliasHandle extends AnnotationHandler<Alias> {




    /**
     * 获取被注解的字段
     * @param targetClazz
     * @return List<String>
     */
    public List<String> getUsedColumn(Class targetClazz) {
        return super.getUsedColumn(Alias.class, targetClazz);
    }

    public Map<String, String> getAlias(Class targetClazz) {
        Map<String, String> map = new HashMap<>();
        List<String> columns = getUsedColumn(targetClazz);
        columns.forEach(column ->{
            Field[] fields = targetClazz.getDeclaredFields();
            for(Field field : fields) {
                if (field.getName().equals(column)) {
                    String alias = field.getAnnotation(Alias.class).value();
                    map.put(alias, column);
                }
            }
        });
        return map;
    }

}
