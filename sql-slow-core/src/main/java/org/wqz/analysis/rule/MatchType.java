package org.wqz.analysis.rule;

/**
 * @Author huhaitao21
 * @Description 匹配类型
 * @Date 15:50 2022/11/9
 **/
public enum MatchType {
    EQUAL("等于"),
    GREATER("大于"),
    LESS("小于"),
    CONTAIN("包含");

    /**
     * 匹配类型
     */
    private String type;

    MatchType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
