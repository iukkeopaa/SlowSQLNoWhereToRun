package org.wqz.analysis.out;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wqz.analysis.score.SqlScoreResult;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @Description: 输出结果到mq
 * @Author: wjh
 * @Date: 2025/4/17 下午3:40
 */
public class SqlScoreResultOutServiceDefault implements SqlScoreResultOutService {

//    private static final Logger logger = LoggerFactory.getLogger(SqlScoreResultOutServiceDefault.class);
//
//    @Override
//    public void outResult(SqlScoreResult sqlScoreResult) {
//        logger.info("======================================分析结果===================================");
//        AtomicInteger atomicInteger = new AtomicInteger(1);
//        if (sqlScoreResult == null) {
//            return;
//        }
//        Consumer<SqlScoreResultDetail> printConsumer = item->{
//            logger.info("=============({})命中规则===================",atomicInteger.getAndIncrement());
//            logger.info("规则命中原因:{}",item.getReason());
//            logger.info("规则命中,修改建议:{}",item.getSuggestion());
//            logger.info("规则命中,{}{}分",item.getScoreDeduction() <0?"加上分数:+":"减去分数", -item.getScoreDeduction());
//
//        };
//        if (sqlScoreResult.getNeedWarn() != null) {
//            logger.info("分析中的 SQL 语句 ID：{}",sqlScoreResult.getSqlId());
//            if (Boolean.TRUE.equals(sqlScoreResult.getNeedWarn())){
//                logger.error("SQL 分析结果的分数为:{},低于预期值请判断是否修改", sqlScoreResult.getScore());
//                if (sqlScoreResult.getAnalysisResults() != null) {
//                    sqlScoreResult.getAnalysisResults().forEach(printConsumer);
//                }
//            }else {
//
//                logger.info("SQL 分析结果的分数为:{},分析正常", sqlScoreResult.getScore());
//                logger.info("=====给出的修改建议如下=====");
//                sqlScoreResult.getAnalysisResults().forEach(printConsumer);
//                logger.info("=========================");
//            }
//        }
//        logger.info("========================================结束=====================================");
//    }


    private static Logger logger = LoggerFactory.getLogger(SqlScoreResultOutServiceDefault.class);

    @Override
    public void outResult(SqlScoreResult sqlScoreResult) {
        if(sqlScoreResult==null){
            return;
        }
        if(sqlScoreResult.getNeedWarn()!=null && sqlScoreResult.getNeedWarn()){
            logger.error("sql analysis result score:{}",sqlScoreResult.getScore());
            if(sqlScoreResult.getAnalysisResults()!=null){
                sqlScoreResult.getAnalysisResults().forEach(result->{
                    logger.error("sql analysis result detail-reason:{},suggestion:{}",result.getReason(),result.getSuggestion());
                });
            }
        }
    }
}
