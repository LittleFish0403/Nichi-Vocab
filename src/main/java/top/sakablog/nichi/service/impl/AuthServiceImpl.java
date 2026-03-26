package top.sakablog.nichi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.UserMapper;
import top.sakablog.nichi.model.User;
import top.sakablog.nichi.model.UserAuth;
import top.sakablog.nichi.model.dto.UserBaseDto;
import top.sakablog.nichi.model.dto.UserLoginDto;
import top.sakablog.nichi.model.dto.UserRegisterDto;
import top.sakablog.nichi.model.enums.IdentityType;
import top.sakablog.nichi.service.AuthService;
import top.sakablog.nichi.service.MailService;
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public User register(UserRegisterDto userRegisterDto) {
        if (userRegisterDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "注册信息不能为空");
        }
        if (userAuthService.existsByIdentityTypeAndIdentifier(
                userRegisterDto.getIdentityType(),
                userRegisterDto.getIdentifier())) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识已存在");
        }

        try {
            String verifyCode = stringRedisTemplate.opsForValue().get("nichi:verify_code:" + userRegisterDto.getIdentityType() + ":" + userRegisterDto.getIdentifier());
            if (verifyCode == null || !verifyCode.equals(userRegisterDto.getVerifyCode())) {
                throw new BusinessException(ResultCode.PARAM_ERROR, "验证码错误或已过期");
            }
            stringRedisTemplate.delete("nichi:verify_code:" + userRegisterDto.getIdentityType() + ":" + userRegisterDto.getIdentifier());

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
    public String generateVerifyCode(IdentityType identityType, String identifier) {
        if (identityType == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证类型不能为空");
        }
        if (identifier == null || identifier.isBlank()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "认证标识不能为空");
        }
        if (! (Validator.isMobile(identifier) || Validator.isEmail(identifier))) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "手机/邮箱格式不正确");
        }
        if (userAuthService.existsByIdentityTypeAndIdentifier(identityType, identifier)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "该手机号或邮箱已注册");
        }

        try {
            String verifyCode = RandomUtil.randomNumbers(6);
            String redisKey = "nichi:" + "verify_code:" + identityType + ":" + identifier;
            stringRedisTemplate.opsForValue().set(redisKey, verifyCode, 5, java.util.concurrent.TimeUnit.MINUTES);
            if (IdentityType.EMAIL.equals(identityType)) {
                try {
                    mailService.sendVerifyCode(identifier, verifyCode);
                } catch (Exception e) {
                    stringRedisTemplate.delete(redisKey);
                    throw e;
                }
            }
            return verifyCode;
        } catch (Exception e) {
            log.error("生成验证码失败: {}", e.getMessage());
            throw new SystemException("生成验证码失败", e);
        }
    }


    @Override
    public UserBaseDto login(UserLoginDto userLoginDto) {
        if (userLoginDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "登录信息不能为空");
        }
        try {
            UserAuth userAuth = userAuthService.findByIdentityTypeAndIdentifier(userLoginDto.getIdentityType(), userLoginDto.getIdentifier());
            if (! (BCrypt.checkpw(userLoginDto.getCredential(), userAuth.getCredential()))) {
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
}
