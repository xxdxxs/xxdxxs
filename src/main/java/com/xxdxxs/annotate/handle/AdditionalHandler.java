package com.xxdxxs.annotate.handle;

import com.xxdxxs.annotate.Additional;
import com.xxdxxs.db.component.Supplementary;
import com.xxdxxs.entity.Entity;
import com.xxdxxs.exception.OperationException;
import com.xxdxxs.utils.ApplicationContextHolder;
import com.xxdxxs.utils.EntityMapper;
import com.xxdxxs.validation.Validation;
import net.bytebuddy.ByteBuddy;
import org.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author xxdxxs
 */
public class AdditionalHandler extends AnnotationHandler<Additional> {

    private Class<? extends Entity> targetClazz;

    private AdditionalHandler(){}

    public AdditionalHandler(Class targetClazz){
        this.targetClazz = targetClazz;
    }


    /**
     * 获取被注解的字段
     * @return List<String>
     */
    public List<String> getUsedColumn() {
        Assert.notNull(targetClazz, "targetClazz must not be null");
        return super.getUsedColumn(Additional.class, targetClazz);
    }

    /**
     * 是否使用了该注解
     * @return
     */
    public boolean isUsed() {
        if (getUsedColumn().isEmpty()) {
            return false;
        }
        return true;
    }

    public <E extends Entity> E addAttribute(E entity) {
        return (E) addAttribute(Arrays.asList(entity)).get(0);
    }




    public <T extends Entity> List<T> addAttribute(List<? extends Entity> list) {
        List<T> targetEntityList = new ArrayList<>();
        Map<String, List<Serializable>> map = getOrignalCodes(list);
        List<Field> fields = getUsedFields();
        list.forEach(o -> {
            fields.forEach(field -> {
                try {
                    field.setAccessible(true);
                    Serializable code = (Serializable) field.get(o);
                    field.setAccessible(true);
                    String columnName = field.getName();
                    String addColumn = field.getAnnotation(Additional.class).column();
                    String daoName = field.getAnnotation(Additional.class).daoName();
                    Supplementary supplementary = (Supplementary) ApplicationContextHolder.getBeanByName(daoName);
                    if (supplementary == null) {
                        throw new OperationException("daoName of Additional annotation must not be null");
                    }
                    Map<? extends Serializable, String> valueMap = supplementary.findNamesByCodes(map.get(columnName));
                    Class dynamicType = new ByteBuddy()
                            .subclass(targetClazz)
                            .defineField(addColumn, String.class, Modifier.PRIVATE)
                            .make()
                            .load(getClass().getClassLoader())
                            .getLoaded();
                    //对新增的属性赋值
                    T target = (T) targetClazz.newInstance();
                    target = (T)dynamicType.newInstance();
                    Field addField = target.getClass().getDeclaredField(addColumn);
                    addField.setAccessible(true);
                    addField.set(target, valueMap.get(code));
                    //把原有的属性值赋值给新实体
                    EntityMapper.copyAttribute(o, target);
                    targetEntityList.add(target);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            });
        });
        return targetEntityList;
    }

    /**
     * 获取使用该注解的field集合
     * @return
     */
    public List<Field> getUsedFields() {
        List<Field> fieldList = new ArrayList<>();
        List<String> columns = getUsedColumn();
        columns.forEach(column -> {
            Field[] fields = targetClazz.getDeclaredFields();
            for(Field field : fields) {
                if (field.getName().equals(column)) {
                    fieldList.add(field);
                }
            }
        });
        return fieldList;
    }

    /**
     * 获取要转换的字段的code值
     * 根据字段名称分组，把该集合中所有该字段的code值集合
     *
     * @param list
     * @return
     */
    public Map<String, List<Serializable>> getOrignalCodes(List<? extends Entity> list) {
        Map<String, List<Serializable>> map = new HashMap<>();
        List<Serializable> codeValues = new ArrayList<>();
        List<Field> fieldList = getUsedFields();
        fieldList.forEach(field -> {
            list.forEach(object ->{
                if (!Validation.isClass(object, targetClazz)) {
                    throw new IllegalArgumentException("object is not instance of target class");
                }
                try {
                    field.setAccessible(true);
                    Serializable value = (Serializable) field.get(object);
                    codeValues.add(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            map.put(field.getName(), codeValues);
        });
        return map;
    }

}
