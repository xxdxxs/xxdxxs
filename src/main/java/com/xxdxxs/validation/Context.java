package com.xxdxxs.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.service.FormHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private FormHandler formHandler;

    private Map<String, List<String>> message = new LinkedHashMap<>();

    private Map<RuleChain, Boolean> results = new HashMap<>();

    public Map<String, List<String>> getMessage() {
        return message;
    }

    public void setFormHandler(FormHandler formHandler){
       this.formHandler = formHandler;
    }

    public FormHandler getFormHandler() {
        return formHandler;
    }

    public Map<RuleChain, Boolean> getResults() {
        return results;
    }

    public String getErrorMessages() {
        String msg = "";
        try {
            msg =  new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            msg = ValidatorEnum.ERROE_GET_EXCEPTION.getMsg();
        }
        return msg;
    }

}
