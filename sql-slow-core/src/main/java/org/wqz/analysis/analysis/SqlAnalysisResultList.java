package org.wqz.analysis.analysis;

import java.util.List;

/**
 * @Description: sql语句解析之后的结果集合
 * @Author: wjh
 * @Date: 2025/4/17 下午2:52
 */
public class SqlAnalysisResultList {

    private List<SqlAnalysisResult> resultList;

    public List<SqlAnalysisResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<SqlAnalysisResult> resultList) {
        this.resultList = resultList;
    }
}
