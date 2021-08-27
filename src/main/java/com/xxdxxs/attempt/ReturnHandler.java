package com.xxdxxs.attempt;

import com.xxdxxs.annotate.handle.AdditionalHandler;
import com.xxdxxs.entity.Entity;
import com.xxdxxs.exception.OperationException;
import com.xxdxxs.utils.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理方法的返回值
 * @author xxdxxs
 */
public class ReturnHandler {

    public static <E extends Entity> List<Map<String, Object>> attach(List<E> entityList) {
        List<Map<String, Object>> list = new ArrayList<>();
        entityList.forEach(e -> {
            list.add(attach(e));
        });
        return list;
    }


    /**
     * 根据Additional注解附加字段
     * @param entity
     * @param <E>
     * @return
     */
    public static <E extends Entity> Map<String, Object> attach(E entity) {
        AdditionalHandler additionalHandler = new AdditionalHandler(entity.getClass());
        E newEntity = entity;
        if (additionalHandler.isUsed()) {
            newEntity = additionalHandler.addAttribute(entity);
        }
        Map<String, Object> map;
        try {
            map = JsonUtils.toMap(JsonUtils.from(newEntity));
        } catch (IOException e) {
           throw new OperationException("entity to map appear exception");
        }
        return map;
    }
}
