package top.sakablog.nichi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDocConfig
 * <p>
 * SpringDoc配置类
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Nichi-项目API文档",
        version = "1.0",
        description = "SpringBoot项目接口文档"
))
public class SpringDocConfig {
    // 无需额外配置，注解已定义基本信息
}
