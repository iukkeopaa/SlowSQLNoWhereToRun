package org.wqz.analysis.analysis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wqz.analysis.extract.SqlExtractResult;
import org.wqz.analysis.utils.GsonUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: sql语句的分析器
 * @Author: wjh
 * @Date: 2025/4/17 下午2:49
 */
public class SqlAnalysis {

    private static Logger LOGGER = LoggerFactory.getLogger(SqlAnalysis.class);

    /**
     * 定义mysq的版本
     */
    private static String mysqlVersion;


    /**
     * 定义sql语句的解析的流程  主函数
     */

    public static SqlAnalysisResultList analysis(SqlExtractResult sqlExtractResult, Connection connection) throws SQLException {

        if (sqlExtractResult == null){
            return null;
        }

        String sourceSql = sqlExtractResult.getSourceSql();

        List<SqlAnalysisResult> resultList = new ArrayList<>();

        SqlAnalysisResult sqlAnalysisResutlDto = null;

        String analysisSql = getAnalysisSql(sourceSql);

        PreparedStatement preStat = null;

        ResultSet resSet = null;


        try {
            preStat = connection.prepareStatement(analysisSql);

            resSet = preStat.executeQuery();

            while (resSet.next()) {
                sqlAnalysisResutlDto = convertSqlAnalysisResultDto(resSet);
                resultList.add(sqlAnalysisResutlDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if (preStat != null) {
                    preStat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        LOGGER.info("sql analysis result = " + GsonUtil.bean2Json(sqlAnalysisResutlDto));
        SqlAnalysisResultList sqlAnalysisResultList = new SqlAnalysisResultList();
        sqlAnalysisResultList.setResultList(resultList);
        return sqlAnalysisResultList;
    }

    private static String getAnalysisSql(String sourceSql) {

        sourceSql = "explain " + sourceSql;
        return sourceSql;
    }


    /**
     * 转换分析结果
     * @param resultSet
     * @return
     */
    private static SqlAnalysisResult convertSqlAnalysisResultDto(ResultSet resultSet){
        SqlAnalysisResult sqlAnalysisResult = new SqlAnalysisResult();

        if (resultSet == null){
            return null;
        }

        try {
            if(StringUtils.isBlank(mysqlVersion)){
                mysqlVersion = getMysqlVersion(resultSet);
            }
            Long id = resultSet.getLong("id");
            String selectType = resultSet.getString("select_type");
            String table = resultSet.getString("table");
            String type = resultSet.getString("type");
            String possibleKeys = resultSet.getString("possible_keys");
            String key = resultSet.getString("key");
            String keyLen = resultSet.getString("key_len");
            String ref = resultSet.getString("ref");
            String rows = resultSet.getString("rows");
            String extra = resultSet.getString("Extra");

            sqlAnalysisResult.setId(id);
            sqlAnalysisResult.setSelectType(selectType);
            sqlAnalysisResult.setTable(table);
            sqlAnalysisResult.setType(type);
            sqlAnalysisResult.setPossibleKeys(possibleKeys);
            sqlAnalysisResult.setKey(key);
            sqlAnalysisResult.setKeyLen(keyLen);
            sqlAnalysisResult.setRef(ref);
            sqlAnalysisResult.setRows(rows);
            sqlAnalysisResult.setExtra(extra);
            if(mysqlVersion.equals(MysqlVersion.MYSQL_5_7.getVersion())){
                Double filtered = resultSet.getDouble("filtered");
                String partitions = resultSet.getString("partitions");
                sqlAnalysisResult.setPartitions(partitions);
                sqlAnalysisResult.setFiltered(filtered);
            }

        } catch (SQLException e) {
           LOGGER.error("sql analysis convert error",e);
            e.printStackTrace();
        }

        return sqlAnalysisResult;
    }

    public static String getMysqlVersion(ResultSet rs){
        //根据返回字段数识别5.6 或者 5.7以上版本
        String mysqlVersion = MysqlVersion.MYSQL_5_6.getVersion();
        try {
            int columnCount = rs.getMetaData().getColumnCount();
            if(columnCount>10){
                mysqlVersion = MysqlVersion.MYSQL_5_7.getVersion();
            }
        } catch (Exception e) {
            LOGGER.error("sql analysis 获取mysql版本异常",e);
        }
        return mysqlVersion;
    }
}
