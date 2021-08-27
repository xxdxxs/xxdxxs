package com.xxdxxs.db.component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * 用于辅助字段code转name，提供查询接口
 * 结合注解Additional使用
 * @author xxdxxs
 */
public interface Supplementary<D extends Supplementary> {

    /**
     * 根据code获取对应信息（code - name）
     * 编码对应中文
     * @param list
     * @return
     */
    Map<Serializable, String> findNamesByCodes(List<? extends Serializable> list);
}
