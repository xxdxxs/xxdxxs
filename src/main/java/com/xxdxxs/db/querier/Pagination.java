package com.xxdxxs.db.querier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int page;

    private int size;

    private long startIndex;

    private long endIndex;

    private long total;

    private long pageTotal;

    private List<E> results = new ArrayList<>();

    public Pagination(int page, int size) {
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String limit() {
        this.startIndex = (this.page - 1) * this.size;
        String pageStr = " limit " + startIndex + "," + size;
        return pageStr;
    }
}
