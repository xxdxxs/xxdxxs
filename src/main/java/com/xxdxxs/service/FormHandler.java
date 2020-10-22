package com.xxdxxs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormHandler {
    private Map<String, Object> data = new LinkedHashMap();

    public FormHandler(Map<String, Object> data){
        this.data = data;
    }

    public static FormHandler ofJson(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map data;
        try {
            data = objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>(){});
        } catch (JsonProcessingException e) {
            data = null;
        }
        return new FormHandler(data);
    }

    public Map<String, Object> getData(){
        return this.data;
    }

    public static void main(String[] args) {
        String str = "{\"userName\":\"小李飞刀\",\"age\":18,\"addTime\":[1,2,3,4]}";
        FormHandler formHandler = FormHandler.ofJson(str);
        System.out.println(formHandler.getData());
    }

}
