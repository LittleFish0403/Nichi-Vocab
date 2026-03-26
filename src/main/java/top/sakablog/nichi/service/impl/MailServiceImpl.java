package top.sakablog.nichi.service.impl;

import cn.hutool.core.lang.Validator;
import lombok.extern.slf4j.Slf4j;
import org.dromara.email.jakarta.api.MailClient;
import org.dromara.email.jakarta.comm.entity.MailMessage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.config.Sms4jMailProperties;
import top.sakablog.nichi.service.MailService;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    private final ObjectProvider<MailClient> mailClientProvider;
    private final Sms4jMailProperties properties;

    public MailServiceImpl(ObjectProvider<MailClient> mailClientProvider, Sms4jMailProperties properties) {
        this.mailClientProvider = mailClientProvider;
        this.properties = properties;
    }

    @Override
    public void sendVerifyCode(String email, String verifyCode) {
        if (!Validator.isEmail(email)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "邮箱格式不正确");
        }
        if (!properties.isEnabled()) {
            throw new SystemException("邮件服务未启用，请先配置 sms4j.mail.enabled=true");
        }

        MailClient mailClient = mailClientProvider.getIfAvailable();
        if (mailClient == null) {
            throw new SystemException("邮件客户端未初始化，请检查邮件配置");
        }

        try {
            MailMessage message = MailMessage.Builder()
                    .mailAddress(email)
                    .title(properties.getVerifyCodeSubject())
                    .body(buildVerifyCodeContent(verifyCode))
                    .build();
            mailClient.send(message);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("发送邮箱验证码失败: {}", e.getMessage(), e);
            throw new SystemException("发送邮箱验证码失败", e);
        }
    }

    private String buildVerifyCodeContent(String verifyCode) {
        return "您的验证码是：" + verifyCode + "，5分钟内有效。若非本人操作，请忽略此邮件。";
    }
}
