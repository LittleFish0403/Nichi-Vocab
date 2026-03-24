package top.sakablog.nichi.service;

import top.sakablog.nichi.model.dto.UserBaseDto;
import top.sakablog.nichi.model.dto.UserRegisterDto;
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
    UserBaseDto register(UserRegisterDto userRegisterDto);

    /**
     * 按认证标识登录。
     */
    UserBaseDto login(IdentityType identityType, String identifier, String credential);

    /**
     * 退出登录。
     */
    void logout();
}
