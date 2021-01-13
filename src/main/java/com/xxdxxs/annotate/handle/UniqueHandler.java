package com.xxdxxs.annotate.handle;

import com.xxdxxs.annotate.Unique;

import java.util.List;


/**
 * 定义唯一数据注解处理类
 *
 * @author xxdxxs
 */
public class UniqueHandler extends AnnotationHandler<Unique> {

    /**
     * 获取被注解的字段
     * @param targetClazz
     * @return List<String>
     */
    public List<String> getUsedColumn(Class targetClazz) {
        return super.getUsedColumn(Unique.class, targetClazz);
    }
}
