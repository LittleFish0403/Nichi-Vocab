package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.*;
import top.sakablog.nichi.repository.WordMasteryRepository;
import top.sakablog.nichi.service.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * WordMasteryServiceImpl
 * <p>
 * WordMasteryService的实现类，提供用户单词掌握度管理的具体业务逻辑实现
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Service
public class WordMasteryServiceImpl implements WordMasteryService {
    @Autowired
    WordMasteryRepository wordMasteryRepository;

    @Autowired
    WordService wordService;

    @Autowired
    private UserBookProgressService userBookProgressService;


    /**
     * 初始化用户单词本单词关系
     */
    @Transactional
    public List<WordMastery> initWordMasteries(Long UserBookProgressId, List<Word> words) {
        try {
            UserBookProgress userBookProgress = userBookProgressService.findUserBookProgressById(UserBookProgressId);
            LocalDateTime now = LocalDateTime.now();
            List<WordMastery> relations = words.stream().map(word -> {
                return new WordMastery()
                        .setUserBookProgress(userBookProgress)
                        .setWord(word)
                        .setWordId(word.getId())
                        .setLastPracticeDate(null)
                        .setNextPracticeDate(now); // 立即进入“今日复习”
            }).toList();

            return wordMasteryRepository.saveAll(relations);
        } catch (Exception e) {
            throw new SystemException("初始化用户单词本单词关系出现问题: " + e.getMessage());
        }
    }

    /**
     * 根据用户单词本关系ID获取用户单词掌握情况
     */
    public List<WordMastery> fetchLearningWordMasteries(Long UserBookProgressId, Integer limit){
        try {
            return wordMasteryRepository.findLearningWordMasteries(UserBookProgressId, limit);
        } catch (Exception e) {
            throw new SystemException("获取用户单词掌握情况出现问题: " + e.getMessage());
        }
    }
}
