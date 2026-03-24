package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.User;
import top.sakablog.nichi.model.UserAuth;
import top.sakablog.nichi.model.enums.IdentityType;
import top.sakablog.nichi.repository.UserAuthRepository;
import top.sakablog.nichi.service.UserAuthService;
import top.sakablog.nichi.service.UserService;

import java.util.List;

/**
 * UserAuthServiceImpl
 * <p>
 * 用户认证信息服务实现。
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserAuth createUserAuth(Long userId, IdentityType identityType, String identifier, String credential) {
        if (userId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        if (identityType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证类型不能为空");
        }
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识不能为空");
        }
        if (credential == null || credential.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "凭证不能为空");
        }
        if (userAuthRepository.existsByIdentityTypeAndIdentifier(identityType, identifier)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识已存在");
        }

        try {
            User user = userService.findUserByUserId(userId);
            UserAuth userAuth = new UserAuth()
                    .setUser(user)
                    .setUserId(userId)
                    .setIdentityType(identityType)
                    .setIdentifier(identifier)
                    .setCredential(credential);
            return userAuthRepository.save(userAuth);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建用户认证信息失败: {}", e.getMessage());
            throw new SystemException("创建用户认证信息失败", e);
        }
    }

    @Override
    public List<UserAuth> findByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        try {
            return userAuthRepository.findByUserId(userId);
        } catch (Exception e) {
            log.error("查询用户认证信息失败: {}", e.getMessage());
            throw new SystemException("查询用户认证信息失败", e);
        }
    }

    @Override
    public UserAuth findByIdentityTypeAndIdentifier(IdentityType identityType, String identifier) {
        if (identityType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证类型不能为空");
        }
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识不能为空");
        }
        try {
            return userAuthRepository.findByIdentityTypeAndIdentifier(identityType, identifier)
                    .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "用户认证信息不存在"));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询用户认证信息失败: {}", e.getMessage());
            throw new SystemException("查询用户认证信息失败", e);
        }
    }

    @Override
    public boolean existsByIdentityTypeAndIdentifier(IdentityType identityType, String identifier) {
        if (identityType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证类型不能为空");
        }
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识不能为空");
        }
        try {
            return userAuthRepository.existsByIdentityTypeAndIdentifier(identityType, identifier);
        } catch (Exception e) {
            log.error("检查认证标识是否存在失败: {}", e.getMessage());
            throw new SystemException("检查认证标识是否存在失败", e);
        }
    }
}
