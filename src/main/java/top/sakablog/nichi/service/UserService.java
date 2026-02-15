package top.sakablog.nichi.service;

import top.sakablog.nichi.model.User;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserService {
    /**
     * 新建用户
     */
    User createUser(String name, String password);

    /**
     * 根据用户名查询用户登录信息
     */
    User findUserByUsername(String username);

    /**
     * 根据用户ID查询用户信息
     */
    User findUserByUserId(Long userId);

    /**
     * 删除用户
     */
    void deleteUserById(Long userId);

    /**
     * 更新用户信息
     */
    User updateUser(User user);

    /**
     * 判断userid是否存在
     */
    boolean existsByUserId(Long userId);
}
