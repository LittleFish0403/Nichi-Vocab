package top.sakablog.nichi.service;

import top.sakablog.nichi.model.UserAuth;
import top.sakablog.nichi.model.enums.IdentityType;

import java.util.List;

/**
 * UserAuthService
 * <p>
 * 用户认证信息服务接口，定义用户登录标识与凭证相关的业务方法。
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UserAuthService {
    /**
     * 创建用户认证信息。
     */
    UserAuth createUserAuth(Long userId, IdentityType identityType, String identifier, String credential);

    /**
     * 根据用户 ID 查询全部认证信息。
     */
    List<UserAuth> findByUserId(Long userId);

    /**
     * 根据标识类型和标识值查询认证信息。
     */
    UserAuth findByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);

    /**
     * 检查标识是否已存在。
     */
    boolean existsByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);
}
