package org.wqz.analysis.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wqz.analysis.score.SqlScoreResult;

/**
 * @Description: 输出结果到mq
 * @Author: wjh
 * @Date: 2025/4/17 下午3:35
 */
public class SqlScoreResultOutMq implements SqlScoreResultOutService{
    private static final Logger logger = LoggerFactory.getLogger(SqlScoreResultOutMq.class);

    @Override
    public void outResult(SqlScoreResult sqlScoreResult) {
        // 待实现
        logger.info("====待实现MQ发送==={}",sqlScoreResult);
    }
}
