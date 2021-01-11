package com.xxdxxs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxdxxs.utils.Convertable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FormHandler implements Convertable<String, Object> {

    /**
     * 数据存放
     */
    private Map<String, Object> data = new LinkedHashMap();

    public FormHandler(Map<String, Object> data) {
        this.data = data;
    }

    public static FormHandler ofJson(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map data;
        try {
            data = objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            data = null;
        }
        return new FormHandler(data);
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    @Override
    public Object getConvertable(String key) {
        return data.get(key);
    }


    public static void main(String[] args) {
        String str = "{\"userName\":\"小李飞刀\",\"age\":18,\"addTime\":[1,2,3,4],\"price\":{\"nums\":222,\"hhh\":444}}";
        FormHandler formHandler = FormHandler.ofJson(str);
        System.out.println(formHandler.getData());
        System.out.println(formHandler.getList("addTime"));
        System.out.println(formHandler.getMap("price").get().get("nums"));
    }

}
