package top.sakablog.nichi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.User;
import top.sakablog.nichi.model.UserInfo;
import top.sakablog.nichi.repository.UserRepository;
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

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(String name, String password) {
        try {
            User user = new User()
                    .setUsername(name)
                    .setPassword(password)
                    .setAvatarUrl("/avatar/default.jpg");
            return userRepository.save(user);
        } catch (SystemException e) {
            log.error("创建用户失败: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public User findUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            log.error("用户名不能为空");
           throw new BusinessException("用户名不能为空");
        }
        if (!userRepository.existsUserByUsername(username)) {
            log.error("用户不存在: {}", username);
            throw new BusinessException("用户不存在");
        }
        try {
            return userRepository.findByusername(username);
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

    @Override
    public User findUserByUserId(Long userId){
        if (!userRepository.existsById(userId)) {
            log.error("用户不存在: {}", userId);
            throw new BusinessException("用户不存在");
        }
        try {
            return userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("用户不存在: {}", userId);
            throw new BusinessException("用户不存在");
        }
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("删除用户失败: {}", e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

    @Override
    public User updateUser(User user){
        User existingUser = userRepository.findById(user.getUser_id())
                .orElseThrow(() -> new BusinessException("用户不存在"));
        try{
            if (user.getAvatarUrl() != null) {
                existingUser.setAvatarUrl(user.getAvatarUrl());
            }
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            return existingUser;
        } catch (Exception e){
            log.error("更新用户失败: {}", e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
