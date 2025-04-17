package org.wqz.analysis.replace;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wqz.analysis.utils.DuccMonitorUtil;
import org.wqz.analysis.utils.GsonUtil;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author huhaitao21
 * @Description sql替换配置
 * @Date 18:50 2023/5/25
 **/
public class SqlReplaceConfig {

    static Logger log  = LoggerFactory.getLogger(SqlReplaceConfig.class);

    /**
     * 配置明细
     */
    private static HashMap<String,String> sqlReplaceMap = new HashMap<>();

    /**
     * 初始化配置
     */
    public static void initConfig(){
        try{
            String configStr = DuccMonitorUtil.getDuccConfig();
            if(StringUtils.isNotBlank(configStr)){
                sqlReplaceMap =  GsonUtil.json2Bean(configStr,HashMap.class);
            }
        }catch (Exception e){
            log.error("sql analysis replace config init error :",e);
        }
    }

    /**
     * 根据sqlid 获取替换sql
     * @param sqlId
     * @return
     */
    public static String getReplaceSqlBySqlId(String sqlId){
        if(StringUtils.isNotBlank(sqlId) && Objects.nonNull(sqlReplaceMap)){
            return sqlReplaceMap.get(sqlId);
        }
        return null;
    }

    /**
     * 获取sql替换映射对象
     * @return
     */
    public static HashMap<String, String> getSqlReplaceMap() {
        return sqlReplaceMap;
    }
}
