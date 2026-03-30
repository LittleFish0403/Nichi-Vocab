package top.sakablog.nichi.service;

import top.sakablog.nichi.model.entity.user.User;
import top.sakablog.nichi.model.dto.user.UserBaseDto;
import top.sakablog.nichi.model.dto.auth.UserLoginDto;
import top.sakablog.nichi.model.dto.auth.UserRegisterDto;
import top.sakablog.nichi.model.enums.IdentityType;

/**
 * AuthService
 * <p>
 * 认证服务接口，负责整合用户主体信息与认证信息。
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AuthService {
    /**
     * 注册用户，并创建对应认证信息。
     */
    User register(UserRegisterDto userRegisterDto);

    /**
     * 生成临时验证码
     */
    String generateVerifyCode(IdentityType identityType, String identifier);

    /**
     * 按认证标识登录。
     */
    UserBaseDto login(UserLoginDto userLoginDto);

    /**
     * 退出登录。
     */
    void logout();
}
