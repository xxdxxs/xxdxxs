package com.xxdxxs.db.jdbc;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataFilter {

    private Map<String, Object> map = new LinkedHashMap<>();

    public Operate operate(){
        return new Operate();
    }

    public Map<String, Object> ofFilter(){
        return this.map;
    }

    public static DataFilter of(){
        return new DataFilter();
    }

    public DataFilter set(String key, String value){
        this.map.put(key, value);
        return this;
    }


    public static void main(String[] args) {
        DataFilter dataFilter = DataFilter.of();
        //select a, b, c from a where b=1 or (c=2 and d=3)
        //list(struct(true, map(b=1)), struct(false. list(c=2, d=3)
        //Select (columns, table, list(struct(and, list(where))...)
        //select().from("a", "a).whereEqual("b", 1).or(() -> whereIn("c", 1, 2, 3))
        //Select List<Where>
    }
}
