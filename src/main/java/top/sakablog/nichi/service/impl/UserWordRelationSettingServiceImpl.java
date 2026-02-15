package top.sakablog.nichi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.model.UserWordRelationSetting;
import top.sakablog.nichi.repository.UserWordRelationSettingRepository;
import top.sakablog.nichi.service.UserWordRelationSettingService;

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
public class UserWordRelationSettingServiceImpl implements UserWordRelationSettingService {
    @Autowired
    UserWordRelationSettingRepository userWordRelationSettingRepository;

    /**
     * 初始化用户单词设置
     */
    @Override
    public UserWordRelationSetting initUserWordRelationSetting() {
        UserWordRelationSetting userWordRelationSetting = new UserWordRelationSetting();
        // 设置默认值
        userWordRelationSetting.setNumberOfWordsPerSession(15);
        userWordRelationSetting.setFeedbackSize(3);
        userWordRelationSetting.setActiveWindowSize(6);
        return userWordRelationSetting;
    }

    /**
     * 获取用户每组单词数量
     */
    public Integer getNumberOfWordsPerSession(Long UserWordBookRelationId){
        if (!userWordRelationSettingRepository.existsById(UserWordBookRelationId)){
            throw new BusinessException("未找到对应的用户单词本设置");
        }
        try {
            UserWordRelationSetting setting = userWordRelationSettingRepository.findById(UserWordBookRelationId).orElseThrow();
            return setting.getNumberOfWordsPerSession();
        } catch (Exception e) {
            throw new BusinessException("获取用户单词本设置失败");
        }
    }

    /**
     * 获取用户单词设置
     */
    public UserWordRelationSetting getUserWordRelationSetting(Long userWordBookRelationId){
        if (!userWordRelationSettingRepository.existsById(userWordBookRelationId)){
            throw new BusinessException("未找到对应的用户单词本设置");
        }
        try {
            return userWordRelationSettingRepository.findById(userWordBookRelationId).orElseThrow();
        } catch (Exception e) {
            throw new BusinessException("获取用户单词本设置失败");
        }
    }

    /**
     * 获取用户单词回传大小
     */
    public Integer getFeedBackSize(Long userWordBookRelationId){
        if (!userWordRelationSettingRepository.existsById(userWordBookRelationId)){
            throw new BusinessException("未找到对应的用户单词本设置");
        }
        try {
            UserWordRelationSetting setting = userWordRelationSettingRepository.findById(userWordBookRelationId).orElseThrow();
            return setting.getFeedbackSize();
        } catch (Exception e) {
            throw new BusinessException("获取用户单词本设置失败");
        }
    }

}
