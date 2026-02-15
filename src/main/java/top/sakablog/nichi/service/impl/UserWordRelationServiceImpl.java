package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.*;
import top.sakablog.nichi.repository.UserWordBookRelationRepository;
import top.sakablog.nichi.repository.UserWordRelationRepository;
import top.sakablog.nichi.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class UserWordRelationServiceImpl implements UserWordRelationService {
    @Autowired
    UserWordRelationRepository userWordRelationRepository;

    @Autowired
    UserWordRelationSettingService userWordRelationSettingService;

    @Autowired
    WordService wordService;

    @Autowired
    private UserWordBookRelationRepository userWordBookRelationRepository;

    /**
     * 初始化用户单词本单词关系
     */
    @Transactional
    public List<UserWordRelation> initUserWordRelation(Long UserWordBookRelationId, List<Word> words) {
        try {
            UserWordBookRelation userWordBookRelation = userWordBookRelationRepository.findById(UserWordBookRelationId)
                    .orElseThrow(() -> new BusinessException("用户单词本关系不存在，ID: " + UserWordBookRelationId));
            LocalDateTime now = LocalDateTime.now();
            List<UserWordRelation> relations = words.stream().map(word -> {
                return new UserWordRelation()
                        .setUserWordBookRelation(userWordBookRelation)
                        .setWord(word)
                        .setWordId(word.getId())
                        .setEasiness(2.5)         // SM2 算法起始简易度
                        .setReps(0)
                        .setStatus(0)             // 0: 学习中
                        .setLearning_status(0)
                        .setReview_status(0)
                        .setLastInterval(0)
                        .setNextInterval(0)
                        .setLastPracticeDate(null)
                        .setNextPracticeDate(now); // 立即进入“今日复习”
            }).toList();

            return userWordRelationRepository.saveAll(relations);
        } catch (Exception e) {
            throw new SystemException("初始化用户单词本单词关系出现问题: " + e.getMessage());
        }
    }

    /**
     * 通过List<Word>创建List<UserWordRelation>
     */
    private List<UserWordRelation> createUserWordRelationsFromWords(List<Word> words, UserWordBookRelation userWordBookRelation) {
        LocalDateTime now = LocalDateTime.now();
        return new ArrayList<>(words.stream().map(word -> {
            return new UserWordRelation()
                    .setUserWordBookRelation(userWordBookRelation)
                    .setWord(word)
                    .setWordId(word.getId())  // 冗余存储ID，方便查询
                    .setEasiness(2.5)         // SM2 算法默认起始简易度为 2.5
                    .setReps(0)               // 初始复习次数为 0
                    .setStatus(0)             // 0: 学习中
                    .setLastInterval(0)       // 初始间隔为 0
                    .setNextInterval(0)       // 初始下次间隔为 0（代表立即可以开始背诵）
                    .setLastPracticeDate(null) // 还没开始背，设为 null
                    .setNextPracticeDate(now); // 设置为当前时间，让它立即出现在“今日需复习”列表中
        }).toList());
    }
}
