package top.sakablog.nichi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.entity.user.User;
import top.sakablog.nichi.model.entity.user.UserProfile;
import top.sakablog.nichi.repository.UserProfileRepository;
import top.sakablog.nichi.repository.UserRepository;
import top.sakablog.nichi.service.UserProfileService;
import top.sakablog.nichi.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Override
    public User createUser(String name) {
        if (name == null || name.isBlank()) {
            throw new BusinessException("用户名不能为空");
        }
        try {
            UserProfile userProfile = new UserProfile().setSelectedBook(null);
            User user = new User()
                    .setUsername(name)
                    .setAvatarUrl("/avatar/default.jpg")
                    .setUserProfile(userProfile);
            userProfile.setUser(user);
            userProfileRepository.save(userProfile);
            return userRepository.save(user);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建用户失败: {}", e.getMessage(), e);
            throw new SystemException("创建用户失败", e);
        }
    }

    @Override
    public User findUserByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new BusinessException("用户名不能为空");
        }
        if (!userRepository.existsUserByUsername(username)) {
            throw new BusinessException("用户不存在");
        }
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error("按用户名查询用户失败: {}", e.getMessage(), e);
            throw new SystemException("按用户名查询用户失败", e);
        }
    }

    @Override
    public User findUserByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (!userRepository.existsById(userId)) {
            throw new BusinessException("用户不存在");
        }
        try {
            return userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            log.error("按用户ID查询用户失败: {}", e.getMessage(), e);
            throw new SystemException("按用户ID查询用户失败", e);
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (!userRepository.existsById(userId)) {
            throw new BusinessException("用户不存在");
        }
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("删除用户失败: {}", e.getMessage(), e);
            throw new SystemException("删除用户失败", e);
        }
    }

    @Override
    public User updateUser(User user) {
        if (user == null || user.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new BusinessException("用户不存在"));

        try {
            if (user.getAvatarUrl() != null) {
                existingUser.setAvatarUrl(user.getAvatarUrl());
            }
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getUserProfile() != null) {
                existingUser.setUserProfile(user.getUserProfile());
            }
            return userRepository.save(existingUser);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("更新用户失败: {}", e.getMessage(), e);
            throw new SystemException("更新用户失败", e);
        }
    }

    @Override
    public boolean existsByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        try {
            return userRepository.existsById(userId);
        } catch (Exception e) {
            log.error("校验用户是否存在失败: {}", e.getMessage(), e);
            throw new SystemException("校验用户是否存在失败", e);
        }
    }
}
