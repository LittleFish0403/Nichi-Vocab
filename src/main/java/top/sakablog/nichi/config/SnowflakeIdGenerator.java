package top.sakablog.nichi.config;

import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;

/**
 * 符合 Hibernate 6.5+ 标准的雪花算法生成器
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 依然调用 Hutool 的强力心脏
        return IdUtil.getSnowflake(1, 1).nextId();
    }
}