package org.wqz.analysis.analysis;

/**
 * @Description: sql解析结果类
 * @Author: wjh
 * @Date: 2025/4/17 下午2:53
 */

//+----+-------------+---------+------+---------------+------+---------+------+------+-------+
//| id | select_type | table   | type | possible_keys | key  | key_len | ref  | rows | Extra |
//+----+-------------+---------+------+---------------+------+---------+------+------+-------+
//|  1 | SIMPLE      | servers | ALL  | NULL          | NULL | NULL    | NULL |    1 | NULL  |
//+----+-------------+---------+------+---------------+------+---------+------+------+-------+
public class SqlAnalysisResult {




    /**
     * 1. id相同时，执行顺序由上至下
     *
     * 2. 如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
     *
     * 3. id如果相同，可以认为是一组，从上往下顺序执行；在所有组中，id值越大，优先级越高，越先执行
     *
     * 4. id为null则最后执行
     */
    private Long id;


    /**
     * 每个select子句的类型,表示当前被分析的sql语句的查询的复杂度,这个字段有多个值
     *
     */

    /**
     * | select_type  | 含义                                                         | 示例                                                         |
     * | ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
     * | SIMPLE       | 简单的 SELECT 查询，不包含子查询或 UNION 操作                | `SELECT column1, column2 FROM table_name;`                   |
     * | PRIMARY      | 查询包含子查询或 UNION 时，最外层的 SELECT 查询类型          | `SELECT column1 FROM table_name WHERE column1 IN (SELECT column1 FROM another_table);` 中最外层的 `SELECT` |
     * | SUBQUERY     | 出现在 SELECT、WHERE 或 HAVING 子句中的子查询部分            | `SELECT column1 FROM table_name WHERE column1 IN (SELECT column1 FROM another_table);` 中的 `(SELECT column1 FROM another_table)` |
     * | DERIVED      | 用于标识在 FROM 子句中使用的子查询（派生表）                 | `SELECT column1 FROM (SELECT column1, column2 FROM table_name WHERE condition) AS derived_table;` 中的 `(SELECT column1, column2 FROM table_name WHERE condition)` |
     * | UNION        | 使用 UNION 操作符连接多个 SELECT 语句时，除第一个 SELECT 语句外，后面的 SELECT 语句类型 | `SELECT column1 FROM table1 UNION SELECT column1 FROM table2;` 中的第二个 `SELECT` |
     * | UNION RESULT | 表示 UNION 操作的结果                                        | `SELECT column1 FROM table1 UNION SELECT column1 FROM table2;` 中展示 UNION 操作结果相关执行计划信息的记录 |
     */
    private String selectType;
    /**
     * 表名称 表示当前访问的表的名称 当from中有子查询时，table字段显示的是<derivedN> N为derived的id的值。
     */
    private String table;

    /**
     * 分区 返回的是数据分区的信息
     */
    private String partitions;
    /**
     * type 这个字段决定mysql如何查找表中的数据，查找数据记录的大概范围。这个字段的所有值表示的从最优到最差依次为： system > const > eq_ref > ref > range > index > all;
     */
    /**
     * | type            | 含义                                                         | 效率排序（从高到低） | 示例                                                         |
     * | --------------- | ------------------------------------------------------------ | -------------------- | ------------------------------------------------------------ |
     * | system          | 表中仅有一行记录，是 const 类型的特例                        | 1                    | 系统表（行数极少的表）                                       |
     * | const           | 表最多有一个匹配行，因为仅有一行，所以可以在查询开始时读取该行 | 2                    | `SELECT * FROM table_name WHERE primary_key = 1;`            |
     * | eq_ref          | 对于每个来自前面的表的行组合，从该表中读取一行。常用于主键或唯一索引的关联查询 | 3                    | `SELECT * FROM table1 JOIN table2 ON table1.id = table2.id;`（`id` 为唯一索引） |
     * | ref             | 对于每个来自前面的表的行组合，所有匹配索引值的行将从这张表中读取。使用非唯一索引进行查找 | 4                    | `SELECT * FROM table_name WHERE non_primary_index = 'value';` |
     * | fulltext        | 使用全文索引进行查找                                         | 5                    | `SELECT * FROM table_name WHERE MATCH(column) AGAINST('keyword' IN NATURAL LANGUAGE MODE);` |
     * | ref_or_null     | 类似于 `ref`，但额外搜索包含 `NULL` 值的行                   | 6                    | `SELECT * FROM table_name WHERE column = 'value' OR column IS NULL;` |
     * | index_merge     | 使用了索引合并优化，即使用多个索引来满足查询条件             | 7                    | `SELECT * FROM table_name WHERE column1 = 'value1' AND column2 = 'value2';`（`column1` 和 `column2` 都有索引） |
     * | unique_subquery | 用于 `IN` 子查询，子查询使用唯一索引                         | 8                    | `SELECT * FROM table1 WHERE id IN (SELECT id FROM table2 WHERE unique_column = 'value');` |
     * | index_subquery  | 用于 `IN` 子查询，子查询使用非唯一索引                       | 9                    | `SELECT * FROM table1 WHERE id IN (SELECT id FROM table2 WHERE non_unique_column = 'value');` |
     * | range           | 只检索给定范围的行，使用一个索引来选择行，通常出现在 `WHERE` 子句中有 `BETWEEN`、`>`、`<`、`IN` 等操作 | 10                   | `SELECT * FROM table_name WHERE column BETWEEN 1 AND 10;`    |
     * | index           | 全索引扫描，和 `ALL` 类似，只不过扫描的是索引树              | 11                   | `SELECT index_column FROM table_name;`（仅扫描索引）         |
     * | ALL             | 全表扫描，需要遍历全量数据                                   | 12                   | `SELECT * FROM table_name;`                                  |
     */
    private String type;

    /**
     * 指出MySQL能使用哪个索引在表中找到记录，查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询使用
     *
     * 如果在进行explain分析sql时，发现这一列有值，但是key列为null，因为mysql觉得可能会使用索引，但是又因为表中的数据很少，使用索引反而没有全表扫描效率高，那么mysql就不会使用索引查找，这种情况是可能发生的。
     *
     * 如果该列是NULL，则没有相关的索引。在这种情况下，可以通过检查 where 子句看是否可以创造一个适当的索引来提
     * 高查询性能，然后用 explain 查看效果。
     * */
    private String possibleKeys;

    /**
     *key列显示MySQL实际决定使用的键（索引）
     * 如果没有选择索引，键是NULL。要想强制MySQL使用或忽视possible_keys列中的索引，在查询中使用FORCE INDEX、USE INDEX或者IGNORE INDEX。
     */
    private String key;

    /**
     * 表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度（key_len显示的值为索引字段的最大可能长度，并非实际使用长度，即key_len是根据表定义计算而得，不是通过表内检索出的）
     * 不损失精确性的情况下，长度越短越好
     * 通过这个字段可以显示具体使用到了索引字段中的哪些列（主要针对联合索引）
     */
    /**
     * 字符串
     *          char(n)：n字节长度
     *
     *          varchar(n)：如果是utf-8，则长度 3n + 2 字节，加的2字节用来存储字符串长度
     *
     * 数值类型
     *          tinyint：1字节
     *
     *          smallint：2字节
     *
     *          int：4字节
     *
     *          bigint：8字节
     *
     * 时间类型
     *            date：3字节
     *
     *            timestamp：4字节
     *
     *            datetime：8字节
     *
     * 如果字段允许为 NULL，需要1字节记录是否为 NULL
     * 索引最大长度是768字节，当字符串过长时，mysql会做一个类似左前缀索引的处理，将前半部分的字符提取出来做索
     * 引。
     */
    private String keyLen;

    /**
     *如果是使用的常数等值查询，这里会显示const，如果是连接查询，被驱动表的执行计划这里会显示驱动表的关联字段，
     * 如果是条件使用了表达式或者函数，或者条件列发生了内部隐式转换，这里可能显示为func
     */
    private String ref;

    /**
     *  表示MySQL根据表统计信息及索引选用情况，估算的找到所需的记录所需要读取的行数
     */
    private String rows;
    /**
     *返回结果的行数占读取行数的百分比，值越大越好
     */
    private Double filtered;

    /**
     * 该列包含MySQL解决查询的详细信息,有以下几种情况：
     *
     * Using where:列数据是从仅仅使用了索引中的信息而没有读取实际的行动的表返回的，这发生在对表的全部的请求列都是同一个索引的部分的时候，表示mysql服务器将在存储引擎检索行后再进行过滤
     *
     * Using temporary：表示MySQL需要使用临时表来存储结果集，常见于排序和分组查询
     *
     * Using filesort：MySQL中无法利用索引完成的排序操作称为“文件排序”
     *
     * Using join buffer：改值强调了在获取连接条件时没有使用索引，并且需要连接缓冲区来存储中间结果。如果出现了这个值，那应该注意，根据查询的具体情况可能需要添加索引来改进能。
     *
     * Impossible where：这个值强调了where语句会导致没有符合条件的行。
     *
     * Select tables optimized away：这个值意味着仅通过使用索引，优化器可能仅从聚合函数结果中返回一行
     */
    private String extra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getPartitions() {
        return partitions;
    }

    public void setPartitions(String partitions) {
        this.partitions = partitions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPossibleKeys() {
        return possibleKeys;
    }

    public void setPossibleKeys(String possibleKeys) {
        this.possibleKeys = possibleKeys;
    }

    public String getKeyLen() {
        return keyLen;
    }

    public void setKeyLen(String keyLen) {
        this.keyLen = keyLen;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Double getFiltered() {
        return filtered;
    }

    public void setFiltered(Double filtered) {
        this.filtered = filtered;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
}
