package top.sakablog.nichi.service;

import top.sakablog.nichi.model.UserWordRelationSetting;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserWordRelationSettingService {
    /**
     * 初始化用户单词设置
     */
    public UserWordRelationSetting initUserWordRelationSetting();

    /**
     * 获取用户每组单词数量
     */
    public Integer getNumberOfWordsPerSession(Long UserWordBookRelationId);

    /**
    * 获取用户单词设置
    */
    public UserWordRelationSetting getUserWordRelationSetting(Long userWordBookRelationId);

    /**
     * 获取用户单词回传大小
     */
    public Integer getFeedBackSize(Long userWordBookRelationId);
}
