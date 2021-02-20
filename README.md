1.参数校验
控制层进行参数校验， 列如参数是个json的字符串

```java
String str = “json字符串”
Validator validator = new Validator(FormHandler.ofJson(str));
validator.set("age", "年龄").must().string()
         .set("phone", "电话").date().must()
         .set("biid", "订单号").date().sometimes()
         .end();
validator.isValid()

```

  must()表示 参数为必要的，则不能为空
  sometimes（）表示非必填
  string()，date()等，代表设定参数的数据类型
  然后执行 validator.isValid(),  结果为boolean

```java
 String str = "{\"owner\":\"小李飞刀\", \"age\": 23, \"startTime\": \"2021-01-05 23:59:50\"}";
        Validator validator = new Validator(FormHandler.ofJson(str));
        validator.set("age", "年龄").must().integer().larger(23, true)
                .set("startTime", "开始查询时间").must().beforeOrEqual(DateUtils.parseString("2021-01-05 														23:59:54"), "yyyy-MM-dd HH:mm:ss")
                .end();
```

可以校验参数值的大小（数值和时间类型）是否在指定的范围内



可以通过addExpandRule方法自定义规则校验参数是否符合要求

```java
 Validator validator = new Validator(FormHandler.ofJson(str));
        validator.set("age", "年龄").must().integer().larger(23, true)
                .set("phone", "电话").date().must().addExpandRule(PhoneRule.getInstance())
                .end();
```

PhoneRule则为自定义的规则类，继承Rule，重写validate方法，完后校验规则的实现

```java
public class PhoneRule extends Rule {
    public static PhoneRule getInstance() {
        return new PhoneRule();
    }

    @Override
    public void validate(RuleChain ruleChain) {
        Object object = getValue(ruleChain);
        execute(ruleChain, object, Validation::isTelephone);
    }
}

```

通过getValue(ruleChain)获取参数值，调用execute方法，传入进行校验的方法  isTelephone 进行回调，回调方法返回一个Boolean

如果参数不符合要求，统一返回错误信息”xxx参数无效”



```java
/**获取或新建formHandler**/
FormHandler formHandler = validator.getForm();
FormHandler formHandler = FormHandler.ofJson(str);
FormHandler formHandler = FormHandler.ofEntity(entity);

/**获取参数的键值对**/
formHandler.getData()
```

也可以直接实例化，通过set方法放入数据



2.操作数据库

```java
ublic class TestDao extends JdbcDao<Entity> {

    public TestDao() {
        super(Entity.class, "tableName");
    }

    @Autowired
    @Override
    public void setTemplate(@Qualifier("JdbcTemplate") JdbcTemplate jdbcTemplate) {
        super.setTemplate(jdbcTemplate);
    }
```

（1）要使用的dao继承JdbcDao，传入表对应的entity， 构造方法设置entity，和对应的要操作的表名

（2）复写父类方法，设置JdbcTemplate

  (3)   还可以通过传入dataSource，或者创建一个ConnectInfo对象，放入数据源的相关信息，设置JdbcTemplate



2.1查询

（2.1.1）

```java
 String str = "{\"name\":\"小李飞刀\",\"age\":\"19\",\"phone\":\"123456789098\",\"startTime\":\"2020-12-01 	00:34:54\",\"endTime\":\"2020-12-10 00:34:54\"}";
FormHandler formHandler = FormHandler.ofJson(str);
ConditionFilter filter = ConditionFilter.of();
filter.setNeedColumns("id", "name", "age", "time", "phone").sort("time", false);
formHandler.ifPresentString("name", filter::equal);
formHandler.ifPresentString("age", filter, Operator.EQUAL);
formHandler.ifPresentString("phone", x -> filter.equal("phone", x));
formHandler.ifPresentString("startTime", x -> filter.greaterThan("time", x));
formHandler.ifPresentString("endTime", x -> filter.lessThan("time", x));
testDao.find(filter)
```

对于查询操作，可以直接利用FormHandler对象，传入一个ConditionFilter对象，进行筛选条件的设置，该对象仅能进行单表的查询

ifPresentString()会过滤值为空的字段不作为查询条件，类似还有ifPresentFloat(), ifPresentDate()等方法



（2.2.2）

```java
Select select = Select.of().from("tableName").columns("id, soco, driverName, phone, carNum")
                .whereIn("carNum", Arrays.asList("鲁123455", "浙123455"))
 								.whereLike("carNum", "6")
  							.whereGreaterEqual("time", date)
  							.sort("crdt");
  testDao.find(select)
```

新建一个select，可以省略调用from方法设置表名，显式调用设置的表名不能与dao构造方法设定的表名不一致

columns设置查询结果需要的字段，也可以columns(String ... column)，



2.2.3

```java
Select select = Select.of().columns("a.biida, a.bity, a.tawe，a.ordeca as ordeca, a.tavo as tavo);
select.from("obma", "a").joinLeft("noinn b", "a.biida", "b.biida")
      .joinLeft("ouden c", "a.biida", "c.biida")
      .joinLeft("inven d", "d.gco", "c.inco")
      .joinLeft("whcon f", "a.whco", "f.gco")
      .whereGreaterThan("a.crdt", time)
      .whereEqual("a.lpco", "sss")
      .whereEqual("a.bity","aaa")
      .sort("a.crdt");
testDao.find(select);                                    
```

from("table", "表别名")，joinLeft("连接的表名 ，别名"， “连接字段”，“连接字段”)，相当于on的关联条件



2.2.4

```java
Select select = Select().of();
select.from("inve").columns("id,gco,gna").whereEqual("id", "123").and()
  .whereGreaterThan("time", "2020-09-08")
  .whereEqual("name", "ww")
  .where(where -> {
    where.whereEqual("soco", "1233").and().whereEqual("addr", "555");
  })
  .whereEqual("whco" ,"123")
  .where(nestWhere -> {
    nestWhere.whereEqual("biid", "1234").or().whereEqual("dssd", "kdka");
  })
  .groupBy("whco, biid, soco")
  .sort("time")
  .sort("name", false)
  .sort("biid", true)
  .limit(1, 100).limit(3, 200).limit(3000);
testDao.find(select);
```

lambda表达式和nestWhere用于给筛选条件加上括号，嵌套查询

2.2.5

```java
Select select = Select.of().from("Drder", "a").columns("count(dwrcode) as num," +
                "a.warecode, w.gco, a.warname ");
        select.joinLeft("whha w", "a.warecode", "w.ewid");
        select.whereEqual("a.delivered", 2);
        JdbcHelper.ifPresent("pTime", startTime, select::whereGreaterEqual);
        JdbcHelper.ifPresent("pTime", endTime, select::whereLessEqual);
        JdbcHelper.ifPresent("aTime", outStartTime, select::whereGreaterEqual);
        JdbcHelper.ifPresent("sTime", outEndTime, select::whereLessEqual);
        select.groupBy("a.warecode");
testDao.find(select);
```

JdbcHelper.ifPresent(). 用于过滤值为空的字段，不作为查询条件



2.2.6 子查询

```java
Select select = Select.of()
                .from(()->
                     Select.of().from("tableName")
                            .whereGreaterThan("age", 21)
                )
                .whereEqual("age", 44)
                .whereLessThan("time", "2021-10-02");

```

或者

```java
 Select nestSelect = Select.of().from("tableName")
                .whereGreaterThan("age", 21);
Select select = Select.of().from(nestSelect)
  .whereEqual("age", 44)
  .whereLessThan("time", "2021-10-02");
```





2.2.7	count

```java
testDao.count(select);
testDao.count(select.count());
```

返回一个int



3. 更新

   3.1

   ```java
    Update update = Update.of().from("test")
                   .set("name", "aaaa")
                   .set("time", new Date())
                   .whereIn("state", Arrays.asList(1,2));
   testDao.update(update)
   ```

   3.2

   ```java
   testDao.update(Entity)
   ```

   排除属性值为null的字段更新

   

   3.3 用注解Unique，标注在可以确定唯一数据的列上，可以是多个字段

   ```java
   testDao().updateByUnique(Entity)
   ```

   根据可确定唯一一条数据的字段（相当于主键）进行更新，排除属性值为null的字段更新

   

   3.4

   ```java
   testDao().upsertByUniqueColumn(Entity)
   ```

   根据可确定唯一数据的字段进行更新，无则插入，有就更新

   3.5 更新自增类型的字段

   场景：如重试次数这一类字段，每次更新该行数据都需要加一，可以用AutoCalculate类包装需要加减的数值，进行更新

   ```java
    Update update = Update.of()
                  .set("age", new AutoCalculate( 10))
                  .whereEqual("name", "XXDXXS");
   ```

   也可以用‘INCREMENT’， ’DECREMENT‘，显示表示加减

   ```java
    Update update = Update.of()
                  .set("age", new AutoCalculate(AutoCalculate.Sign.INCREMENT,10))
                  .whereEqual("name", "XXDXXS");
   ```

   

​	

4.新增

```java
testDao().insert(Insert insert)
testDao().batchInsert(List<E> entitys)
testDao().insert(Map<String, ? extends Serializable> map)
testDao().insert(String sql)
```



5.删除

```java
testDao().delete(Delete delete)
testDao().delete(String column, Serializable value)
```

根据某一个字段作为过滤条件进行删除，也可自己定义delete对象传入delete方法中



6.工具类

xmlUtils：

```java
List<Map<String, String>> fromXml(String xml, String targetParentNode)
```

传入xml字符串和想要的节点数据的父节点，返回该父节点下所有的元素键值对集合



StringUtils:

继承了org.springframework.util.StringUtils



MapUtils:

```java
/**只保留集合中的key**/
<T> Map<String, T> retainKeys(Map<String, T> map, List<String> keys)
  
/**移除集合中的key**/
<T> Map<String, T> removeKeys(Map<String, T> map, List<String> keys)
  
/**移除map中值为null的键值对**/  
<T> Map<String, T> removeNullValue(Map<String, T> map)

```



JsonUtils:

```java
String from(Object data)
  
Map<String, Object> toMap(String text)
  
List<Map<String, Object>> toMapList(String text)
  
Map<String, Map<String, Object>> toMapKeyed(String text)
  
Map<String, List<Map<String, Object>>> toMapGrouped(String text)
  
<E> E to(String text, Class<E> clazz)
  
/**提供想要的属性所在的位置，如data/infos/name，获取最后name的值**/
String getValueByPath(String jsonStr, String path) 

......
```



HttpUtils：目前还不完善，不能过多的自定义请求的参数值，只有基础的功能实现

```java
/**
 * @param url 地址
 * @param businessParam 参数字符串
 * @return HttpEntity
 */
HttpEntity doPost(String url, String businessParam);

/**
 * 用http模拟webservice请求
 *
 * @param requestUrl 请求地址
 * @param requestParam 请求参数
 * @param user 用户
 * @param password 密码
 * @return String
 */
String doWSByHttp(String requestUrl, String requestParam, String user, String password)

```



ExcelRead:基础功能实现，不够完善

```java
List<String[]> getExcelData(MultipartFile file)
```



ExcelExport:基础功能实现，不够完善

```java
void buildExcelDocument(List<?> list, String[] showName, String[] fieldName, String filename,
       String fileType, HttpServletRequest request, HttpServletResponse response, String type)
```



EntityMapper:实体映射器

```java
/**entity 转 object**/
<E extends Entity> Map<String, ? extends Serializable> objectToMap
 
/**entity 转 object， 移除非私有的，是基础数据类型的，是集合或map的属性**/ 
<E extends Entity> Map<String, ? extends Serializable> objectToMap  

/**比较两个实体类，返回属性值不同的字段**/
<E extends Entity> List<String> compareValue(E oEntity, E newEntity)  
```



DateUtils:日期时间的工具类，各种时间格式的转换













