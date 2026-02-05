package top.sakablog.nichi.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.UserMapper;
import top.sakablog.nichi.model.dto.UserDto;
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
@RequestMapping("/api/v1/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/userInfo")
    public RestResponse<UserDto> getUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        return RestResponse.success(userMapper.toDto(userService.findUserByUserId(userId)));
    }

}

