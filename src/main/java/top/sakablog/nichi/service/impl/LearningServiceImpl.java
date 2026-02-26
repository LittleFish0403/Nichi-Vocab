package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.algorithm.Sm2Algorithm;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.WordMasteryMapper;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.UserBookProgress;
import top.sakablog.nichi.model.WordMastery;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.WordMasteryDto;
import top.sakablog.nichi.model.dto.WordMasteryScoreDto;
import top.sakablog.nichi.repository.WordMasteryRepository;
import top.sakablog.nichi.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * LearningServiceImpl
 * <p>
 * LearningService的实现类，提供获取待学习单词和更新单词掌握度的具体业务逻辑实现
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Service
@Slf4j
public class LearningServiceImpl implements LearningService {
    /**
     * 实例注入
     */
    @Autowired
    private UserBookProgressService userBookProgressService;

    @Autowired
    private WordMasteryService wordMasteryService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private WordMasteryRepository wordMasteryRepository;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordMasteryMapper wordMasteryMapper;

    /**
     * 获取待学习单词：顺序抽取
     */
    @Override
    public List<WordMasteryDto> fetchWordsByOrderForSession(Long userBookProgressId, Long userId){
        try {
            Integer numberOfWordsPerSession = userProfileService.getNumberOfWordsPerSession(userId);

            List<WordMastery> learningWords = wordMasteryService.fetchLearningWordMasteries(userBookProgressId, numberOfWordsPerSession);
            List<WordMastery> combinedWords = new ArrayList<>(learningWords);
            log.info("当前已学习单词数量: {}", learningWords.size());
            UserBookProgress userBookProgress = userBookProgressService.findUserBookProgressById(userBookProgressId);

            List<Word> newWords = wordMasteryRepository.findNewWordsByOrderForLimit(userBookProgress.getBookId(), userBookProgressId, numberOfWordsPerSession- combinedWords.size());

            combinedWords.addAll(wordMasteryService.initWordMasteries(userBookProgressId, newWords));

            return wordMasteryMapper.toDtoList(combinedWords);

        } catch (Exception e) {
            throw new SystemException("获取待学习单词出现问题: " + e.getMessage());
        }
    }

    /**
     * 获取待学习单词：顺序抽取,回传大小
     */
    @Override
    public List<WordMasteryDto> fetchWordsByOrderForSync(Long userBookProgressId, Long userId){
        try {
            UserBookProgress userBookProgress = userBookProgressService.findUserBookProgressById(userBookProgressId);
            Integer feedBackSize = userProfileService.getFeedBackSize(userId);
            List<Word> newWords = wordMasteryRepository.findNewWordsByOrderForLimit(userBookProgress.getBookId(), userBookProgressId, feedBackSize);
            return wordMasteryMapper.toDtoList(wordMasteryService.initWordMasteries(userBookProgressId, newWords));
        } catch (Exception e) {
            throw new SystemException("获取待学习单词出现问题: " + e.getMessage());
        }
    }

    /**
     * 获取待学习单词：顺序抽取,指定数量
     */
    @Override
    public List<WordMasteryDto> fetchWordsByOrderForSession(Long userBookProgressId, int count){
        return null;
    }

    /**
     * 获取待学习单词：乱序抽取
     */
    @Override
    public List<WordMasteryDto> fetchWordsByRandomForSession(Long userBookProgressId){
        return null;
    }

    @Override
    public List<WordMasteryDto> fetchWordsByRandomForSession(Long userBookProgressId, int count){
        return null;
    }

    /**
     * 获取待学习单词：按单元抽取
     */
    @Override
    public List<WordMasteryDto> fetchWordsByUnitForSession(Long userBookProgressId, String unit){
        return null;
    }



    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<WordMasteryDto> updateWordMastery(List<WordMasteryScoreDto> scoreDtos, Long UserBookProgressId) {
        List<WordMasteryDto> results = new ArrayList<>();

        for (WordMasteryScoreDto scoreDto : scoreDtos) {
            // 1. 获取或初始化
            WordMastery relation = wordMasteryRepository.findById(scoreDto.getWordMasteryId())
                    .orElseThrow(() -> new BusinessException("用户单词关系不存在，ID: " + scoreDto.getWordMasteryId()));

            // 如果状态为0且学习次数没到三次，说明一轮学习还未完成，不更新状态
            if (scoreDto.getLearnStatus() != 3 && relation.getStatus() == 0) {
                relation.setLearnStatus(scoreDto.getLearnStatus());
                WordMastery saved = wordMasteryRepository.save(relation);
                results.add(wordMasteryMapper.toDto(saved));
            }

            // 如果状态为1且复习次数没到三次，说明一轮复习还未完成，不更新状态
            else if (scoreDto.getReviewStatus() != 3 && relation.getStatus() == 1) {
                relation.setReviewStatus(scoreDto.getReviewStatus());
                WordMastery saved = wordMasteryRepository.save(relation);
                results.add(wordMasteryMapper.toDto(saved));
            }

            // 更新状态
            else {
                // 2. 执行 SM2 算法逻辑 (核心逻辑注入)
                Sm2Algorithm.ReviewResult result = Sm2Algorithm.calculate(
                        scoreDto.getScore(),
                        relation.getReps(),
                        relation.getEasiness(),
                        relation.getNextInterval() // 这里建议用当前间隔作为计算基数
                );

                // 3. 将算法结果回写到实体
                relation.setReps(result.reps);
                relation.setEasiness(result.ef);
                relation.setLastInterval(relation.getNextInterval()); // 记录旧间隔
                relation.setNextInterval(result.interval);           // 更新新间隔
                relation.setStatus(result.status);
                relation.setLearnStatus(0); // 重置学习状态
                relation.setReviewStatus(0);   // 重置复习状态

                LocalDateTime now = LocalDateTime.now();
                relation.setLastPracticeDate(now);
                relation.setNextPracticeDate(now.plusDays(result.interval));

                // 4. 保存并转换返回
                WordMastery saved = wordMasteryRepository.save(relation);
                results.add(wordMasteryMapper.toDto(saved));
            }
        }

        return results; // 记得返回结果，给前端更新 UI
    }
}
