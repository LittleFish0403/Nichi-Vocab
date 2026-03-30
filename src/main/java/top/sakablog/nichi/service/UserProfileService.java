package top.sakablog.nichi.service;

import top.sakablog.nichi.model.dto.user.UserProfileDto;

/**
 * UserProfileService
 * <p>
 * 用户信息服务接口
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserProfileService {
    /**
     * 根据用户ID获取用户信息
     */
    UserProfileDto getUserProfileByUserId(Long userId);

    /**
     * 获取用户每组单词数量
     */
    Integer getNumberOfWordsPerSession(Long userId);

    /**
     * 获取用户单词回传大小
     */
    Integer getFeedBackSize(Long userId);
}
