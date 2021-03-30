package com.xxdxxs.db.jdbc;

import com.xxdxxs.db.component.JdbcHelper;
import com.xxdxxs.db.querier.AbstractSpace;
import com.xxdxxs.entity.Entity;
import com.xxdxxs.exception.JdbcException;
import com.xxdxxs.exception.enums.JdbcErrorMsg;
import com.xxdxxs.service.ConditionFilter;
import com.xxdxxs.service.TransForm;
import com.xxdxxs.support.*;
import com.xxdxxs.utils.EntityMapper;
import com.xxdxxs.utils.MapUtils;
import com.xxdxxs.utils.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Types;
import java.util.*;
import java.util.function.Function;

/**
 * jdbc操作类
 * @author xxdxxs
 */
public abstract class JdbcDao<E extends Entity> extends AbstractDao<JdbcTemplate> {

    private Table table;

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private DataSource dataSource;

    public JdbcDao(Class<E> clazz, String tableName) {
        table = new Table();
        this.table.setTableName(tableName);
        this.table.ofClass(clazz);
    }

    public void setTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = JdbcTemplate.of(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        setTemplate(jdbcTemplate);
    }


    @Override
    public void setTemplate(JdbcTemplate jdbcTemplate) {
        super.setTemplate(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public void setTemplate(org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
        setTemplate(JdbcTemplate.of(jdbcTemplate));
    }

    private List<E> find(String sql, Map<String, Object> paramsMap, RowMapper<E> rowMapper) {
        List<E> list = namedParameterJdbcTemplate.query(sql, paramsMap, rowMapper);
        return list;
    }

    public List<E> find(ConditionFilter filter) {
        Select select = TransForm.Turn.of(filter).getQuerier(table.getTableName());
        return find(select);
    }

    public E findOne(ConditionFilter filter) {
        Select select = TransForm.Turn.of(filter).getQuerier(table.getTableName());
        return findOne(select);
    }

    public List<E> find(Select select) {
        checkPresentTable(select, select::from);
        RowMapper<E> rowMapper = new BeanPropertyRowMapper(table.getClazz());
        return find(select.toString(), select.getParams(), rowMapper);
    }

    public E findOne(Select select) {
        checkPresentTable(select, select::from);
        select.limit(1);
        RowMapper<E> rowMapper = new BeanPropertyRowMapper(table.getClazz());
        E entity;
        try {
            entity = namedParameterJdbcTemplate.queryForObject(select.toString(), select.getParams(), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return entity;
    }

    public List<E> findAll() {
        Select select = Select.of().from(table.getTableName());
        return find(select);
    }

    public List<?> findToClass(Select select, Class<?> clazz) {
        checkPresentTable(select, select::from);
        RowMapper rowMapper = new BeanPropertyRowMapper(clazz);
        List<?> list = find(select.toString(), select.getParams(), rowMapper);
        return list;
    }

    public List<E> findByColumn(String column, Serializable value) {
        Select select = Select.of().from(this.table.getTableName()).where(column, value);
        return find(select);
    }

    public List<E> find(String sql) {
        RowMapper<E> rowMapper = new BeanPropertyRowMapper(table.getClazz());
        List<E> list = jdbcTemplate.query(sql, rowMapper);
        return list;
    }

    public int count(String sql) {
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int count(Select select) {
        checkPresentTable(select, select::from);
        return namedParameterJdbcTemplate.queryForObject(select.count().toString(), select.getParams(), Integer.class);
    }


    public int count(Select.Count count) {
        Select select = count.getSelect();
        checkPresentTable(select, select::from);
        return namedParameterJdbcTemplate.queryForObject(count.toString(), count.getSelect().getParams(), Integer.class);
    }

    public int countByColumn(String column, Serializable value) {
        Select select = Select.of().from(this.table.getTableName()).where(column, value);
        return namedParameterJdbcTemplate.queryForObject(select.count().toString(), select.getParams(), Integer.class);
    }

    /**
     * update, 排除属性值为null的字段更新
     * @param entity
     * @return
     */
    public int update(E entity) {
        Map<String, ? extends Serializable> map = EntityMapper.objectToMap(entity, true);
        return update(map);
    }


    public int update(Update update) {
        checkPresentTable(update, update::from);
        return namedParameterJdbcTemplate.update(update.toString(), update.getParams());
    }

    public int updateDefineTypes(Update update) {
        if (Arrays.stream(update.getTypes()).anyMatch(k -> k == Types.OTHER)) {
            return update(update);
        }
        return jdbcTemplate.update(update.stringfy(Column.DEFINED), update.getArgs(), update.getTypes());
    }

    public int update(String key, Serializable value) {
        Update update = Update.of().from(this.table.getTableName()).set(new Column(key, value));
        return update(update);
    }

    public int update(Map<String, ? extends Serializable> map) {
        Update update = Update.of().from(this.table.getTableName());
        map.forEach((k, v) -> {
            update.set(map);
        });
        return update(update);
    }

    public int update(Map<String, ? extends Serializable> map, LinkedHashMap<String, Object> criterions) {
        Update update = Update.of().from(this.table.getTableName());
        map.forEach((k, v) -> {
            update.set(map);
        });
        criterions.forEach((k, v) -> {
            update.where(k, v);
        });
        return update(update);
    }


    private Boolean update(String sql, Map<String, Object> map) {
        return namedParameterJdbcTemplate.update(sql, map) > 0;
    }


    /**
     * 根据可确定唯一数据行的字段更新
     * 根据isRemoveNullValue参数判断是否排除属性值为null的字段更新
     * @param entity
     * @return
     */
    public int updateByUnique(E entity, boolean isRemoveNullValue) {
        List<String> uniqueColumns = JdbcHelper.getUniqueColumn(entity);
        if (uniqueColumns.isEmpty()) {
            throw new JdbcException(JdbcErrorMsg.NOT_EXIST_UNIQUE_COLUMN);
        }
        Map<String, ? extends Serializable> map = EntityMapper.objectToMap(entity, isRemoveNullValue);
        LinkedHashMap<String, Object> criterions = new LinkedHashMap<>();
        for (String uniqueColumn : uniqueColumns) {
            Object value = map.get(uniqueColumn);
            if (value == null) {
                throw new JdbcException(JdbcErrorMsg.UNIQUE_COLUMN_IS_NULL);
            }
            criterions.put(uniqueColumn, value);
            map.remove(uniqueColumn);
        }
        return update(map, criterions);
    }

    /**
     * 根据可确定唯一数据行的字段更新
     * 默认排除属性值为null的字段更新
     * @param entity
     * @return
     */
    public int updateByUnique(E entity) {
        return updateByUnique(entity, true);
    }


    /**
     * 根据定义唯一数据的字段，插入或更新
     * 根据isRemoveNullValue参数是否移除实体类中的值为null的字段
     * @param entity
     * @return
     */
    public boolean upsertByUniqueColumn(E entity, boolean isRemoveNullValue) {
        List<String> uniqueColumns = JdbcHelper.getUniqueColumn(entity);
        if (uniqueColumns.isEmpty()) {
            throw new JdbcException(JdbcErrorMsg.NOT_EXIST_UNIQUE_COLUMN);
        }
        Map<String, ? extends Serializable> map = EntityMapper.objectToMap(entity, isRemoveNullValue);
        Select select = Select.of();
        LinkedHashMap<String, Object> criterions = new LinkedHashMap<>();
        uniqueColumns.stream().forEach(x -> {
            Object value = map.get(x);
            if (value == null) {
                throw new JdbcException(JdbcErrorMsg.UNIQUE_COLUMN_IS_NULL);
            }
            select.whereEqual(x, value);
            criterions.put(x, value);
        });

        List<E> list = find(select);
        /**表中不存在就插入**/
        if(list.isEmpty()) {
            return insert(entity);
        }
        if(list.size() > 1){
            throw new JdbcException(JdbcErrorMsg.DATA_BY_UNIQUE_COLUMN_MORETHAN_ONE);
        }
        E originalEntity = list.get(0);
        List<String> chanageColumns = EntityMapper.compareValue(originalEntity, entity);
        Map<String, ? extends Serializable> paramValues = MapUtils.retainKeys(map, chanageColumns);
        return update(paramValues, criterions) > 0;
    }


    /**
     * 根据定义唯一数据的字段，插入或更新
     * 默认移除实体类中值为null的字段
     * @param entity
     * @return
     */
    public boolean upsertByUniqueColumn(E entity) {
        return upsertByUniqueColumn(entity, true);
    }

    public Boolean insert(E entity) {
        Map<String, ? extends Serializable> map = EntityMapper.objectToMap(entity);
        return insert(map);
    }

    public Boolean insert(Insert insert) {
        checkPresentTable(insert, insert::into);
        insert.into(this.table.getTableName());
        return update(insert.toString(), insert.getParams());
    }

    private int doBatchInsert(List<E> entitys) {
        Map<String, ? extends Serializable>[] maps = new HashMap[entitys.size()];
        for (int i = 0; i < entitys.size(); i++) {
            maps[i] = EntityMapper.objectToMap(entitys.get(i));
        }
        Insert insert = Insert.of().into(this.table.getTableName());
        maps[0].forEach((k, v) -> {
            insert.set(maps[0]);
        });
        String sql = insert.toString();
        int[] nums = namedParameterJdbcTemplate.batchUpdate(sql, maps);
        return Arrays.stream(nums).sum();
    }


    public int batchInsert(List<E> entitys) {
        if (entitys.size() <= 1000) {
            return doBatchInsert(entitys);
        }
        return batchInsert(entitys, 1000);
    }


    public int batchInsert(List<E> entitys, int onceNum) {
        int resNum = 0;
        if (onceNum > 2000) {
            throw new JdbcException(JdbcErrorMsg.OVER_LIMIT_COMMIT);
        }
        int length = entitys.size();
        if (length < onceNum) {
            resNum = doBatchInsert(entitys);
        } else {
            int num = length / onceNum;
            List<E> reList = new ArrayList<>();
            for (int i = 0; i < (num + 1); i++) {
                if ((i + 1) * onceNum < length) {
                    reList = entitys.subList(i * onceNum, (i + 1) * onceNum);
                } else {
                    reList = entitys.subList(i * onceNum, length);
                }
                resNum = resNum + doBatchInsert(reList);
            }
        }
        return resNum;
    }


    public Boolean insert(Map<String, ? extends Serializable> map) {
        Insert insert = Insert.of().into(this.table.getTableName());
        map.forEach((k, v) -> {
            insert.set(map);
        });
        return insert(insert);
    }

    public Boolean insert(String sql) {
        return jdbcTemplate.update(sql) > 0;
    }


    public Boolean delete(Delete delete) {
        checkPresentTable(delete, delete::from);
        return update(delete.toString(), delete.getParams());
    }


    public Boolean delete(String column, Serializable value) {
        Delete delete = Delete.of().from(table.getTableName()).where(column, value);
        return update(delete.toString(), delete.getParams());
    }


    /**
     * 检查是否已设置表名，并检查设置的表名与默认的是否一致
     *
     * @param t
     * @param <T>
     * @return
     */
    private <T extends AbstractSpace> T checkPresentTable(T t, Function<String, T> callback) {
        String tableName = t.getTable().getTableName();
        if (!StringUtils.isEmpty(tableName) && !tableName.equalsIgnoreCase(this.table.getTableName())) {
            throw new JdbcException(JdbcErrorMsg.TABLENAME_IS_DIFFERENT);
        }
        if (StringUtils.isEmpty(tableName)) {
            return callback.apply(this.table.getTableName());
        }
        return t;
    }

}
