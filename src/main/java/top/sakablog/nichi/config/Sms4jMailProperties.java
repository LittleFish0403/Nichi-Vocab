package top.sakablog.nichi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "sms4j.mail")
public class Sms4jMailProperties {
    private boolean enabled = false;
    private String configId = "default";
    private String smtpServer;
    private String port = "465";
    private String fromAddress;
    private String username;
    private String password;
    private String nickName = "Nichi";
    private boolean ssl = true;
    private boolean auth = true;
    private int retryInterval = 5;
    private int maxRetries = 1;
    private String verifyCodeSubject = "Nichi 验证码";
}
