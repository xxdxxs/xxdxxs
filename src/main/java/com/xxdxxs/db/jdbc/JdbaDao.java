package com.xxdxxs.db.jdbc;

import com.xxdxxs.db.component.DataFilter;
import com.xxdxxs.db.component.JdbcHelper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xxdxxs
 */
public abstract class JdbaDao<E> {

    private Table table;

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbaDao(Class<E> clazz, String tableName){
        table = new Table();
        this.table.setTableName(tableName);
        this.table.ofClass(clazz);
    }

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    private List<? extends E> find(String sql, Map<String, Object> paramsMap, RowMapper<E> rowMapper){
        List<E> list = namedParameterJdbcTemplate.query(sql, paramsMap, rowMapper);
        return list;
    }


    public List<? extends E> find(Select select){
        String sql = select.toString();
        RowMapper<E> rowMapper = new BeanPropertyRowMapper(table.getClazz());
        return find(select.toString(), select.getParams(), rowMapper);
    }

    public List<? extends E> findAll(){
        Select select = Select.of().from(table.getTableName());
        return find(select);
    }

    public List<?> findToClass(Select select, Class<?> clazz){
        RowMapper rowMapper = new BeanPropertyRowMapper(clazz);
        List<?> list = find(select.toString(),select.getParams(), rowMapper);
        return list;
    }

    public List<? extends E> findByColumn(String column, Serializable value){
        Select select = Select.of().from(this.table.getTableName()).where(column, value);
        return find(select);
    }

    public List<? extends E> find(String sql){
        RowMapper<E> rowMapper = new BeanPropertyRowMapper(table.getClazz());
        List<E> list = jdbcTemplate.query(sql, rowMapper);
        return list;
    }

    public int count(String sql){
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int count(Select select){
        return namedParameterJdbcTemplate.queryForObject(select.count().toString(), select.getParams(), Integer.class);
    }


    public int count(Select.Count count){
        return namedParameterJdbcTemplate.queryForObject(count.toString(), count.getSelect().getParams(), Integer.class);
    }

    public int countByColumn(String column, Serializable value){
        Select select = Select.of().from(this.table.getTableName()).where(column, value);
        return namedParameterJdbcTemplate.queryForObject(select.count().toString(), select.getParams(), Integer.class);
    }

    public List<? extends E> find(DataFilter dataFilter){
        return null;
    }

    public List<? extends E> findOne(DataFilter dataFilter){
        return null;
    }

    public int update(Update update){
        return namedParameterJdbcTemplate.update(update.toString(), update.getParams());
    }

    public int updateDefineTypes(Update update){
        return jdbcTemplate.update(update.stringfy(Update.DEFINED), update.getArgs(), update.getTypes());
    }

    public int update(String key, Serializable value){
        Update update = Update.of().from(this.table.getTableName()).set(new Column(key, value));
        return update(update);
    }

    public int update(Map<String, ? extends Serializable> map){
        Update update = Update.of().from(this.table.getTableName());
        map.forEach((k, v) ->{
            update.set(map);
        });
        return update(update);
    }

    public int updateByCriterions(Map<String, ? extends Serializable> map, LinkedHashMap<String, Object> criterions){
        Update update = Update.of().from(this.table.getTableName());
        map.forEach((k, v) ->{
            update.set(map);
        });
        criterions.forEach((k, v) ->{
            update.where(k, v);
        });
        return update(update);
    }

    public int update(DataFilter dataFilter){
        return 1;
    }

    public int delete(){
        return 1;
    }

    public Boolean insert(E entity){
        return null;
    }

}
