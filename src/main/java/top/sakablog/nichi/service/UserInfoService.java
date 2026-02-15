package top.sakablog.nichi.service;

import top.sakablog.nichi.model.dto.UserInfoDto;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserInfoService {
    /**
     * 根据用户ID获取用户信息
     */
    public UserInfoDto getUserInfoByUserId(Long userId);

    /**
     * 根据用户ID获取用户选择的词书
     */
    public UserInfoDto getUserWordBookByUserId(Long userId);
}
