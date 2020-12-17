package com.xxdxxs.support;

import com.xxdxxs.utils.StringUtils;

public class PrimaryKey {

    private String[] names;

    public void setNames(String[] names) {
        this.names = names;
    }

    /**
     * 是否是自增长的主键
     */
    private boolean isAutoGenerate;

    public PrimaryKey(String... names) {
        new PrimaryKey(names, false);
    }

    public PrimaryKey(String[] names, boolean isAutoGenerate) {
        this.names = names;
        this.isAutoGenerate = isAutoGenerate;
    }

    public boolean exist(){
        if(names == null || names.length == 0){
            return false;
        }
        return true;
    }



}
