package top.sakablog.nichi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.UserMapper;
import top.sakablog.nichi.model.dto.user.UserDto;
import top.sakablog.nichi.model.dto.user.UserProfileDto;
import top.sakablog.nichi.service.UserProfileService;
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
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    @SaCheckLogin
    public RestResponse<UserDto> getUser() {
        Long userId = StpUtil.getLoginIdAsLong();
        return RestResponse.success(userMapper.toDto(userService.findUserByUserId(userId)));
    }

    @GetMapping("/profile")
    @SaCheckLogin
    public RestResponse<UserProfileDto> getUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        log.info("getUserProfile id:{}", userId);
        return RestResponse.success(userProfileService.getUserProfileByUserId(userId));
    }


}

