package top.sakablog.nichi.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.UserMapper;
import top.sakablog.nichi.model.User;
import top.sakablog.nichi.model.dto.UserBaseDto;
import top.sakablog.nichi.model.dto.UserDto;
import top.sakablog.nichi.model.dto.UserRequestDto;
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
@RequestMapping("/api/v1/login/")
@Tag(name="Login Management", description="APIs for user login and registration")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     */
    @PostMapping("/doLogin")
    public RestResponse<UserBaseDto> doLogin(@RequestBody UserRequestDto userDto) {
        // 第一步：比对前端提交的账号名称、密码
        User user = userService.findUserByUsername(userDto.getUsername());
        if(user.getPassword().equals(userDto.getPassword())) {
            // 第二步：根据账号id，进行登录
            StpUtil.login(user.getUser_id());
            return RestResponse.success(userMapper.toBaseDto(user));
        }
        return RestResponse.fail(ResultCode.SYSTEM_ERROR,"登录失败");
    }

    @GetMapping(value = "/tokenInfo")
    public RestResponse<SaTokenInfo> tokenInfo() {
        return RestResponse.success(StpUtil.getTokenInfo());
    }

    @PostMapping("/logout")
    public RestResponse<Boolean> logout() {
        StpUtil.logout();
        return RestResponse.success(true);
    }

    // 查询登录状态
    @GetMapping("isLogin")
    public RestResponse<UserBaseDto> isLogin() {
        System.out.println(">>> 收到 Token: " + StpUtil.getTokenValue());
        System.out.println(">>> 是否登录: " + StpUtil.isLogin());
        StpUtil.checkLogin();
        long userId = StpUtil.getLoginIdAsLong();
        User user = userService.findUserByUserId(userId);
        return RestResponse.success(userMapper.toBaseDto(user));
    }

    /**
     * 用户注册
     */
    @PostMapping("/doRegister")
    @Operation(summary = "用户注册", description = "用户注册接口，提供用户名和密码进行注册")
    public RestResponse<UserBaseDto> doRegister(@RequestBody UserRequestDto userDto) {
        User user = userService.createUser(userDto.getUsername(), userDto.getPassword());
        return RestResponse.success(userMapper.toBaseDto(user));
    }
}
