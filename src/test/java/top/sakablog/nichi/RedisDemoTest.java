package top.sakablog.nichi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */

@SpringBootTest
public class RedisDemoTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("name", "Sakana");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}
