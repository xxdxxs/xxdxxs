package com.xxdxxs.utils;

import com.xxdxxs.annotate.Alias;
import com.xxdxxs.annotate.handle.AliasHandle;
import com.xxdxxs.entity.Entity;
import com.xxdxxs.validation.Context;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * 实体映射器
 *
 * @author xxdxxs
 */
public abstract class EntityMapper {

    /**
     * entity 转 Map
     * @param entity
     * @param <E>
     * @return Map
     */
    public static <E extends Entity> Map<String, ? extends Serializable> objectToMap(E entity) {
        return objectToMap(entity, false);
    }


    /**
     * entity 转 Map， 移除非私有的，是基础数据类型的，是集合或map的属性
     * @param entity
     * @param isRemoveNullValue 是否移除值为null的键值对
     * @param <E>
     * @return Map
     */
    public static <E extends Entity> Map<String, ? extends Serializable> objectToMap(E entity, boolean isRemoveNullValue) {
        Map<String, Serializable> map = new HashMap<>();
        if (entity == null) {
        }
        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(entity) instanceof Collection || field.get(entity) instanceof Map) {
                    continue;
                }
                if (Modifier.PRIVATE != field.getModifiers()) {
                    continue;
                }
                if (field.getType().isPrimitive()) {
                    continue;
                }
                Object object = field.get(entity);
                if (isRemoveNullValue && object == null) {
                    continue;
                }
                map.put(field.getName(), (Serializable) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }



    /**
     * 比较两个实体类，返回属性值不同的字段
     * @param oEntity 原实体
     * @param newEntity  新实体
     * @param <E>
     * @return  List
     */
    public static <E extends Entity> List<String> compareReturnDifferColumn(E oEntity, E newEntity){
        Map<String, ? extends Serializable> oMap = objectToMap(oEntity);
        Map<String, ? extends Serializable> newMap = objectToMap(newEntity);
        List<String> list = new ArrayList<>();
        newMap.forEach( (k ,v) -> {
            if (oMap.containsKey(k)) {
                if (!Objects.equals(v, oMap.get(k))) {
                    list.add(k);
                }
            }
        });
        return list;
    };


    /**
     * 从一个entity赋值给一个新的entity
     * 同名的属性赋值，然后把使用别名注解的字段与原entity字段进行匹配赋值
     * @param oEntity
     * @param targetEntity
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E extends Entity, V extends Entity> V copyAttribute(E oEntity, V targetEntity) {
        //先赋予属性名称一致的值
        BeanUtils.copyProperties(oEntity, targetEntity);
        //获取新实体有别名的字段
        Map<String, String> aliasMap = new AliasHandle().getAlias(targetEntity.getClass());
        aliasMap.forEach((alias, orignName) ->{
            try {
                Field field = oEntity.getClass().getDeclaredField(alias);
                field.setAccessible(true);
                try {
                    Object value = field.get(oEntity);
                    Field targetField = targetEntity.getClass().getDeclaredField(orignName);
                    targetField.setAccessible(true);
                    targetField.set(targetEntity, value);
                } catch (IllegalAccessException e) {
                    throw e.fillInStackTrace();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
        return targetEntity;
    }
}
