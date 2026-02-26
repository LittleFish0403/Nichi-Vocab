package top.sakablog.nichi.config;

import org.hibernate.annotations.IdGeneratorType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义雪花算法注解
 */
@IdGeneratorType(SnowflakeIdGenerator.class) // 指向你的生成器类
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Snowflake {
}