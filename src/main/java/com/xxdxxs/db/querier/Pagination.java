package com.xxdxxs.db.querier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long page;

    private long size;

    private long startIndex;

    private long endIndex;

    private long total;

    private long pageTotal;

    private List<E> results = new ArrayList<>();

    public Pagination(long page, long size) {
        this.page = page;
        this.size = size;
    }

    public List<E> getResults() {
        return results;
    }

    public Pagination<E> setResults(List<E> results) {
        this.results = results;
        return this;
    }

    public Pagination<E> addResult(E result) {
        this.results.add(result);
        return this;
    }


    public String limit(){
        this.startIndex = (this.page -1) * this.size;
        String pageStr = " limit "+ startIndex + "," +size;
        return pageStr;
    }
}
