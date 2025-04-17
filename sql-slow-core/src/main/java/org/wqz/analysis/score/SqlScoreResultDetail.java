package org.wqz.analysis.score;

/**
 * @Description: sql评分的细节结果
 * @Author: wjh
 * @Date: 2025/4/17 下午3:32
 */
public class SqlScoreResultDetail {
    /**
     * 减分值
     */
    private Integer scoreDeduction;

    /**
     * 原因
     */
    private String reason;

    /**
     * 建议
     */
    private String suggestion ;


    /**
     * 是否严格规则，是的-直接触发警告，否-依赖综合评分进行警告（暂不使用）
     */
    private Boolean strict ;


    public Integer getScoreDeduction() {
        return scoreDeduction;
    }

    public void setScoreDeduction(Integer scoreDeduction) {
        this.scoreDeduction = scoreDeduction;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Boolean getStrict() {
        return strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }
}
