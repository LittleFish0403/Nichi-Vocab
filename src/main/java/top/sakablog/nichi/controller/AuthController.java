package top.sakablog.nichi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.UserMapper;
import top.sakablog.nichi.model.dto.auth.UserLoginDto;
import top.sakablog.nichi.model.dto.auth.UserRegisterDto;
import top.sakablog.nichi.model.dto.auth.VerifyCodeRequestDto;
import top.sakablog.nichi.model.dto.user.UserBaseDto;
import top.sakablog.nichi.model.entity.user.User;
import top.sakablog.nichi.service.AuthService;
import top.sakablog.nichi.service.UserService;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/auth/")
@Tag(name="Login Management", description="APIs for user login and registration")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，提供用户名和密码进行登录")
    public RestResponse<UserBaseDto> doLogin(@RequestBody UserLoginDto userLoginDto) {
        return RestResponse.success(authService.login(userLoginDto));
    }

    @GetMapping(value = "/token-info")
    @SaCheckLogin
    public RestResponse<SaTokenInfo> tokenInfo() {
        return RestResponse.success(StpUtil.getTokenInfo());
    }

    @PostMapping("/logout")
    @SaCheckLogin
    public RestResponse<Boolean> logout() {
        authService.logout();
        return RestResponse.success(true);
    }

    @GetMapping("/current")
    @SaCheckLogin
    public RestResponse<UserBaseDto> isLogin() {
        log.info(">>> 收到 Token: " + StpUtil.getTokenValue());
        log.info(">>> 是否登录: " + StpUtil.isLogin());
        long userId = StpUtil.getLoginIdAsLong();
        User user = userService.findUserByUserId(userId);
        return RestResponse.success(userMapper.toBaseDto(user));
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口，提供用户名和密码进行注册")
    public RestResponse<UserBaseDto> doRegister(@RequestBody UserRegisterDto userRegisterDto) {
        return RestResponse.success(userMapper.toBaseDto(authService.register(userRegisterDto)));
    }

    /**
     * 生成验证码
     */
    @GetMapping("/verify-code")
    @Operation(summary = "生成验证码", description = "生成验证码接口，提供认证类型和认证标识（手机号或邮箱）进行验证码生成")
    public RestResponse<Void> generateVerifyCode(@RequestBody VerifyCodeRequestDto verifyCodeRequestDto) {
        authService.generateVerifyCode(verifyCodeRequestDto.getIdentityType(), verifyCodeRequestDto.getIdentifier());
        return RestResponse.success();
    }
}
