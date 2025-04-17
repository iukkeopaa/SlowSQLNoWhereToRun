package org.wqz.analysis.score;

import java.util.List;

/**
 * @Description: sql评分结果
 * @Author: wjh
 * @Date: 2025/4/17 下午3:32
 */
public class SqlScoreResult {
    /**
     * sql id
     */
    private String sqlId;

    /**
     * 执行的原始sql
     */
    private String sourceSql;

    /**
     * 是否需要警告
     */
    private Boolean needWarn;

    /**
     * 综合评分
     */
    private Integer score;

    /**
     * 分析结果明细
     */
    List<SqlScoreResultDetail> analysisResults;

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getSourceSql() {
        return sourceSql;
    }

    public void setSourceSql(String sourceSql) {
        this.sourceSql = sourceSql;
    }

    public Boolean getNeedWarn() {
        return needWarn;
    }

    public void setNeedWarn(Boolean needWarn) {
        this.needWarn = needWarn;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<SqlScoreResultDetail> getAnalysisResults() {
        return analysisResults;
    }

    public void setAnalysisResults(List<SqlScoreResultDetail> analysisResults) {
        this.analysisResults = analysisResults;
    }
}
