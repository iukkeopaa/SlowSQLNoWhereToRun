package org.wqz.analysis.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wqz.analysis.score.SqlScoreResult;

/**
 * @Description: 结果输出到MySQL
 * @Author: wjh
 * @Date: 2025/4/17 下午3:34
 */
public class SqlScoreResultOutMySQL implements SqlScoreResultOutService{

    private static final Logger logger = LoggerFactory.getLogger(SqlScoreResultOutMySQL.class);
    @Override
    public void outResult(SqlScoreResult sqlScoreResult) {
        logger.info("====待实现MySQL[想使用模板方法来抽象]==={}",sqlScoreResult);
    }
}
