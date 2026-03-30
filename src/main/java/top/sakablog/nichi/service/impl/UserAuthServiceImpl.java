package top.sakablog.nichi.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.entity.user.User;
import top.sakablog.nichi.model.entity.user.UserAuth;
import top.sakablog.nichi.model.enums.IdentityType;
import top.sakablog.nichi.repository.UserAuthRepository;
import top.sakablog.nichi.service.UserAuthService;
import top.sakablog.nichi.service.UserService;

import java.util.List;

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
            throw new BusinessException(ResultCode.PARAM_ERROR, "手机号或邮箱不能为空");
        }
        if (credential == null || credential.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "凭证不能为空");
        }
        if (existsByIdentifier(identifier)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "该手机号或邮箱已注册");
        }

        try {
            User user = userService.findUserByUserId(userId);
            // 这里使用 BCrypt 对认证标识进行哈希处理，增加安全性
            String hashedCredential = BCrypt.hashpw(credential, BCrypt.gensalt());

            UserAuth userAuth = new UserAuth()
                    .setUser(user)
                    .setUserId(userId)
                    .setIdentityType(identityType)
                    .setIdentifier(identifier)
                    .setCredential(hashedCredential);
            return userAuthRepository.save(userAuth);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建用户认证信息失败: {}", e.getMessage(), e);
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
            log.error("查询用户认证信息失败: {}", e.getMessage(), e);
            throw new SystemException("查询用户认证信息失败", e);
        }
    }

    @Override
    public UserAuth findByIdentityTypeAndIdentifier(IdentityType identityType, String identifier) {
        if (identityType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证类型不能为空");
        }
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "手机号或邮箱不能为空");
        }
        try {
            return userAuthRepository.findByIdentityTypeAndIdentifier(identityType, identifier)
                    .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "账号未注册"));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询用户认证信息失败: {}", e.getMessage(), e);
            throw new SystemException("查询用户认证信息失败", e);
        }
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "手机号或邮箱不能为空");
        }
        try {
            return userAuthRepository.existsByIdentifier(identifier);
        } catch (Exception e) {
            log.error("校验手机号或邮箱是否已注册失败: {}", e.getMessage(), e);
            throw new SystemException("校验手机号或邮箱是否已注册失败", e);
        }
    }

    @Override
    public boolean existsByIdentityTypeAndIdentifier(IdentityType identityType, String identifier) {
        if (identityType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证类型不能为空");
        }
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "手机号或邮箱不能为空");
        }
        try {
            return userAuthRepository.existsByIdentityTypeAndIdentifier(identityType, identifier);
        } catch (Exception e) {
            log.error("按认证类型校验账号是否已注册失败: {}", e.getMessage(), e);
            throw new SystemException("按认证类型校验账号是否已注册失败", e);
        }
    }
}
