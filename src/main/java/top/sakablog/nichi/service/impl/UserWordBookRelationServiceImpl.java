package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.*;
import top.sakablog.nichi.model.dto.UserWordBookRelationRequestDto;
import top.sakablog.nichi.repository.UserWordBookRelationRepository;
import top.sakablog.nichi.service.UserService;
import top.sakablog.nichi.service.UserWordBookRelationService;
import top.sakablog.nichi.service.UserWordRelationService;
import top.sakablog.nichi.service.UserWordRelationSettingService;

import java.util.List;

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
public class UserWordBookRelationServiceImpl implements UserWordBookRelationService {
    @Autowired
    UserWordBookRelationRepository userWordBookRelationRepository;

    @Autowired
    UserService userService;

    @Autowired
    WordBookServiceImpl wordBookService;

    @Autowired
    UserWordRelationSettingService userWordRelationSettingService;

    @Autowired
    UserWordRelationService userWordRelationService;

    /**
     * 用户选择单词本
     */
    @Override
    @Transactional
    public UserWordBookRelation selectWordBook(Long userId, Long wordBookId){
        if (!userService.existsByUserId(userId) || !wordBookService.existsByWordBookId(wordBookId)) {
            throw new BusinessException("请输入有效的用户ID和单词本ID");
        }
        try {
            User user = userService.findUserByUserId(userId);

            if (userWordBookRelationRepository.existsUserWordBookRelationByUserIdAndWordBookId(userId, wordBookId)) {
                if (user.getUserInfo().getSelectedWordBook().getId().equals(wordBookId)) {
                    throw new BusinessException("重复选择词书");
                }
                return userWordBookRelationRepository.findUserWordBookRelationByUserIdAndWordBookId(userId, wordBookId);
            } else {
                UserWordBookRelation userWordBookRelation = userWordBookRelationRepository.save(new UserWordBookRelation()
                        .setUserInfo(user.getUserInfo())
                        .setWordBook(wordBookService.getWordBookById(wordBookId))
                        .setUserId(userId)
                        .setWordBookId(wordBookId));
                UserWordRelationSetting userWordRelationSetting = userWordRelationSettingService.initUserWordRelationSetting();
                userWordBookRelation.setUserWordRelationSetting(userWordRelationSetting);
                userWordBookRelation.getUserWordRelationSetting().setUserWordBookRelation(userWordBookRelation);
                user.getUserInfo().setSelectedWordBook(userWordBookRelation);
                return userWordBookRelationRepository.save(userWordBookRelation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : e.toString();
            throw new SystemException("选择单词本出现问题: " + errorMsg);
        }
    }

    /**
     * 用户取消选择单词本
     */
    public void unselectWordBook(Long userId, Long wordBookId){
        try {
            userWordBookRelationRepository.deleteUserWordBookRelationByUserIdAndWordBookId(userId, wordBookId);
        } catch (Exception e) {
            throw new SystemException("取消选择单词本出现问题: " + e.getMessage());
        }
    }

    /**
     * 根据用户ID查询用户选择的单词本关系
     */
    public List<WordBook> getSelectedWordBooksByUserId(Long userId){
        try {
            return userWordBookRelationRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new SystemException("根据用户ID查询用户选择的单词本关系出现问题: " + e.getMessage());
        }
    }

    /**
     * 根据ID查找对应的用户单词本关系
     */
    public UserWordBookRelation getUserWordBookRelationById(Long id){
        try {
            return userWordBookRelationRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new SystemException("根据ID查找对应的用户单词本关系出现问题: " + e.getMessage());
        }
    }
}
