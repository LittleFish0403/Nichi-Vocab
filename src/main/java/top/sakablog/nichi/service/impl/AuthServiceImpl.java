package top.sakablog.nichi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.UserMapper;
import top.sakablog.nichi.mapper.UserRegisterMapper;
import top.sakablog.nichi.model.User;
import top.sakablog.nichi.model.UserAuth;
import top.sakablog.nichi.model.UserProfile;
import top.sakablog.nichi.model.dto.UserBaseDto;
import top.sakablog.nichi.model.dto.UserRegisterDto;
import top.sakablog.nichi.model.dto.VerificationCodeDto;
import top.sakablog.nichi.model.enums.IdentityType;
import top.sakablog.nichi.repository.UserRepository;
import top.sakablog.nichi.service.AuthService;
import top.sakablog.nichi.service.UserAuthService;
import top.sakablog.nichi.service.UserService;

/**
 * AuthServiceImpl
 * <p>
 * 认证服务实现，负责注册、登录与登出流程编排。
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public User registerRequest(UserRegisterDto userRegisterDto) {
        if (userRegisterDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "注册信息不能为空");
        }
        if (userAuthService.existsByIdentityTypeAndIdentifier(
                userRegisterDto.getIdentityType(),
                userRegisterDto.getIdentifier())) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识已存在");
        }

        try {
            User user = userService.createUser(userRegisterDto.getUsername());
            userAuthService.createUserAuth(
                    user.getId(),
                    userRegisterDto.getIdentityType(),
                    userRegisterDto.getIdentifier(),
                    userRegisterDto.getCredential()
            );
            return user;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("用户注册失败: {}", e.getMessage());
            throw new SystemException("用户注册失败", e);
        }
    }

    @Override
    @Transactional
    public User registerIdentify(VerificationCodeDto verificationCodeDto) {
            if (verificationCodeDto == null) {
                throw new BusinessException(ResultCode.PARAM_ERROR, "注册信息不能为空");
            }
            if (userAuthService.existsByIdentityTypeAndIdentifier(
                    verificationCodeDto.getIdentityType(),
                    verificationCodeDto.getIdentifier())) {
                throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识已存在");
            }

            try {
                User user = userService.createUser(verificationCodeDto.getUsername());
                userAuthService.createUserAuth(
                        user.getId(),
                        verificationCodeDto.getIdentityType(),
                        verificationCodeDto.getIdentifier(),
                        verificationCodeDto.getCredential()
                );
                return user;
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                log.error("用户注册失败: {}", e.getMessage());
                throw new SystemException("用户注册失败", e);
            }
    }


    @Override
    public UserBaseDto login(IdentityType identityType, String identifier, String credential) {
        if (identityType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证类型不能为空");
        }
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识不能为空");
        }
        if (credential == null || credential.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "凭证不能为空");
        }

        try {
            UserAuth userAuth = userAuthService.findByIdentityTypeAndIdentifier(identityType, identifier);
            if (!matchesCredential(credential, userAuth.getCredential())) {
                throw new BusinessException(ResultCode.UNAUTHORIZED, "用户名或密码错误");
            }

            User user = userAuth.getUser();
            if (user == null) {
                throw new SystemException("认证信息未关联用户主体");
            }

            StpUtil.login(user.getId());
            return userMapper.toBaseDto(user);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("用户登录失败: {}", e.getMessage());
            throw new SystemException("用户登录失败", e);
        }
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    private void applyIdentifier(User user, IdentityType identityType, String identifier) {
        if (identityType == IdentityType.EMAIL) {
            user.setEmail(identifier);
            return;
        }
        if (identityType == IdentityType.PHONE) {
            user.setPhone(identifier);
        }
    }

    // 后续接入 BCrypt 时，只需要替换这一处比对逻辑。
    private boolean matchesCredential(String rawCredential, String storedCredential) {
        return storedCredential != null && storedCredential.equals(rawCredential);
    }
}
