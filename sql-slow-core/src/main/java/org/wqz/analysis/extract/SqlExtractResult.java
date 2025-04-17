package org.wqz.analysis.extract;

/**
 * @Description: 提取sql语句的结果
 * @Author: wjh
 * @Date: 2025/4/17 下午3:11
 */
public class SqlExtractResult {




    /**
     * 基于mybatis 配置的sql id
     */
    private String sqlId;

    /**
     * 待执行，原sql
     */
    private String sourceSql;

    public String getSourceSql() {
        return sourceSql;
    }

    public void setSourceSql(String sourceSql) {
        this.sourceSql = sourceSql;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }
}
