package com.xxdxxs.annotate.handle;

import com.xxdxxs.annotate.Unique;

import java.util.List;


/**
 * 定义唯一数据数据
 *
 * @author xxdxxs
 */
public class UniqueHandler extends AnnotationHandler<Unique> {

    public List<String> getUsedColumn(Class targetClazz) {
        return super.getUsedColumn(Unique.class, targetClazz);
    }
}
