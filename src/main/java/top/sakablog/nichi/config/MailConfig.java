package top.sakablog.nichi.config;

import org.dromara.email.jakarta.api.MailClient;
import org.dromara.email.jakarta.comm.config.MailSmtpConfig;
import org.dromara.email.jakarta.core.factory.MailFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.sakablog.nichi.common.exception.SystemException;

@Configuration
@EnableConfigurationProperties(Sms4jMailProperties.class)
public class MailConfig {

    @Bean
    @ConditionalOnProperty(prefix = "sms4j.mail", name = "enabled", havingValue = "true")
    public MailClient mailClient(Sms4jMailProperties properties) {
        validate(properties);

        MailSmtpConfig config = MailSmtpConfig.builder()
                .smtpServer(properties.getSmtpServer())
                .port(properties.getPort())
                .fromAddress(properties.getFromAddress())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .nickName(properties.getNickName())
                .isSSL(String.valueOf(properties.isSsl()))
                .isAuth(String.valueOf(properties.isAuth()))
                .retryInterval(properties.getRetryInterval())
                .maxRetries(properties.getMaxRetries())
                .build();

        MailFactory.put(properties.getConfigId(), config);
        return MailFactory.createMailClient(properties.getConfigId());
    }

    private void validate(Sms4jMailProperties properties) {
        if (isBlank(properties.getSmtpServer())
                || isBlank(properties.getFromAddress())
                || isBlank(properties.getUsername())
                || isBlank(properties.getPassword())) {
            throw new SystemException("邮件配置不完整，请检查 sms4j.mail 配置");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
