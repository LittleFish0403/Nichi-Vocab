package top.sakablog.nichi.service;

public interface MailService {
    void sendVerifyCode(String email, String verifyCode);
}
