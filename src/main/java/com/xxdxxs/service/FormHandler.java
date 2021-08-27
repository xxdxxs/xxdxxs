package com.xxdxxs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xxdxxs.entity.Entity;
import com.xxdxxs.utils.Convertable;
import com.xxdxxs.utils.JsonUtils;
import com.xxdxxs.utils.MapUtils;
import javafx.beans.binding.ObjectExpression;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

/**
 * @author xxdxxs
 */
public class FormHandler implements Convertable<String, Object> {

    /**
     * 数据存放
     */
    private Map<String, Object> data = new LinkedHashMap();

    public FormHandler(Map<String, Object> data) {
        this.data = data;
    }


    public FormHandler() {

    }

    public static FormHandler ofEntity(Entity entity) {
        Map<String, Object> map = MapUtils.fromEntity(entity);
        return ofJson(JsonUtils.fromMap(map));
    }

    public static FormHandler ofJson(String jsonStr) {
        Map data = null;
        try {
            data = JsonUtils.toMap(jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FormHandler(data);
    }

    public FormHandler set(String key, Object value) {
        Assert.notNull(key, "key must not be null");
        data.put(key, value);
        return this;
    }


    public Map<String, Object> getData() {
        return this.data;
    }

    @Override
    public Object getConvertable(String key) {
        return data.get(key);
    }


    public FormHandler tidy() {
        data = MapUtils.removeNullValue(getData());
        return this;
    }

}
