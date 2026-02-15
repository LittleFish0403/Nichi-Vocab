package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.algorithm.Sm2Algorithm;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.UserWordRelationMapper;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.UserWordBookRelation;
import top.sakablog.nichi.model.UserWordRelation;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.UserWordRelationDto;
import top.sakablog.nichi.model.dto.UserWordRelationScoreDto;
import top.sakablog.nichi.model.dto.WordDto;
import top.sakablog.nichi.repository.UserWordBookRelationRepository;
import top.sakablog.nichi.repository.UserWordRelationRepository;
import top.sakablog.nichi.service.UserWordLearningService;
import top.sakablog.nichi.service.UserWordRelationService;
import top.sakablog.nichi.service.UserWordRelationSettingService;
import top.sakablog.nichi.service.WordService;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Slf4j
public class UserWordLearningServiceImpl implements UserWordLearningService {
    @Autowired
    UserWordRelationRepository userWordRelationRepository;

    @Autowired
    UserWordRelationSettingService userWordRelationSettingService;

    @Autowired
    WordService wordService;

    @Autowired
    private UserWordBookRelationRepository userWordBookRelationRepository;

    @Autowired
    private UserWordBookRelationServiceImpl userWordBookRelationService;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private UserWordRelationMapper userWordRelationMapper;

    @Autowired
    private UserWordRelationService userWordRelationService;

    /**
     * 获取待学习单词：顺序抽取
     */
    @Override
    public List<UserWordRelationDto> getLearningWordsByOrder(Long userWordBookRelationId){
        try {
            List<UserWordRelation> learningWords = userWordRelationRepository.findLearningUserWordRelations(userWordBookRelationId);
            List<UserWordRelation> combinedWords = new ArrayList<>(learningWords);
            log.info("当前已学习单词数量: {}", learningWords.size());
            UserWordBookRelation userWordBookRelation = userWordBookRelationService.getUserWordBookRelationById(userWordBookRelationId);
            Integer numberOfWordsPerSession = userWordRelationSettingService.getNumberOfWordsPerSession(userWordBookRelationId);
            List<Word> newWords = userWordRelationRepository.findNewWordsByOrder(userWordBookRelation.getWordBookId(), userWordBookRelationId, numberOfWordsPerSession- combinedWords.size());

            combinedWords.addAll(userWordRelationService.initUserWordRelation(userWordBookRelationId, newWords));

            return userWordRelationMapper.toDtoList(combinedWords);

        } catch (Exception e) {
            throw new SystemException("获取待学习单词出现问题: " + e.getMessage());
        }
    }

    /**
     * 获取待学习单词：顺序抽取,回传大小
     */
    @Override
    public List<UserWordRelationDto> getLearningWordsByOrderAdditional(Long userWordBookRelationId){
        try {
            UserWordBookRelation userWordBookRelation = userWordBookRelationService.getUserWordBookRelationById(userWordBookRelationId);
            Integer feedBackSize = userWordRelationSettingService.getFeedBackSize(userWordBookRelationId);
            List<Word> newWords = userWordRelationRepository.findNewWordsByOrder(userWordBookRelation.getWordBookId(), userWordBookRelationId, feedBackSize);
            return userWordRelationMapper.toDtoList(userWordRelationService.initUserWordRelation(userWordBookRelationId, newWords));
        } catch (Exception e) {
            throw new SystemException("获取待学习单词出现问题: " + e.getMessage());
        }
    }

    /**
     * 获取待学习单词：顺序抽取,指定数量
     */
    @Override
    public List<WordDto> getLearningWordsByOrder(Long userWordBookRelationId, int count){
        try {
            UserWordBookRelation userWordBookRelation = userWordBookRelationService.getUserWordBookRelationById(userWordBookRelationId);
            return wordMapper.toDtoList(userWordRelationRepository.findNewWordsByOrder(userWordBookRelation.getWordBookId(), userWordBookRelationId, count));

        } catch (Exception e) {
            throw new SystemException("获取待学习单词出现问题: " + e.getMessage());
        }
    }

    /**
     * 获取待学习单词：乱序抽取
     */
    @Override
    public List<WordDto> getLearningWordsByRandom(Long userWordBookRelationId){
        return null;
    }

    @Override
    public List<WordDto> getLearningWordsByRandom(Long userWordBookRelationId, int count){
        return null;
    }

    /**
     * 获取待学习单词：按单元抽取
     */
    @Override
    public List<WordDto> getLearningWordsByUnit(Long userWordBookRelationId, String unit){
        return null;
    }



    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<UserWordRelationDto> updateUserLearningWords(List<UserWordRelationScoreDto> scoreDtos, Long userWordBookRelationId) {
        List<UserWordRelationDto> results = new ArrayList<>();

        for (UserWordRelationScoreDto scoreDto : scoreDtos) {
            // 1. 获取或初始化 Relation
            UserWordRelation relation = userWordRelationRepository.findById(scoreDto.getUserWordRelationId())
                    .orElseThrow(() -> new BusinessException("用户单词关系不存在，ID: " + scoreDto.getUserWordRelationId()));
//                    .orElseGet(() -> {
//                        UserWordBookRelation userWordBookRelation = userWordBookRelationRepository.findById(userWordBookRelationId)
//                                .orElseThrow(() -> new BusinessException("用户单词本关系不存在，ID: " + userWordBookRelationId));
//                        Word word = wordService.getWordById(scoreDto.getWordId());
//
//                        LocalDateTime now = LocalDateTime.now();
//                        UserWordRelation userWordRelation = new UserWordRelation()
//                                .setUserWordBookRelation(userWordBookRelation)
//                                .setWord(word)
//                                .setWordId(word.getId())  // 冗余存储ID，方便查询
//                                .setEasiness(2.5)         // SM2 算法默认起始简易度为 2.5
//                                .setReps(0)               // 初始复习次数为 0
//                                .setStatus(0)             // 0: 学习中
//                                .setLearning_status(0)    // 学习状态初始为 0
//                                .setReview_status(0)      // 复习状态初始为 0
//                                .setLastInterval(0)       // 初始间隔为 0
//                                .setNextInterval(0)       // 初始下次间隔为 0（代表立即可以开始背诵）
//                                .setLastPracticeDate(null) // 还没开始背，设为 null
//                                .setNextPracticeDate(now); // 设置为当前时间，让它立即出现在“今日需复习”列表中
//                        return userWordRelationRepository.save(userWordRelation);
//                    });

            // 如果状态为0且学习次数没到三次，说明一轮学习还未完成，不更新状态
            if (scoreDto.getLearning_status() != 3 && relation.getStatus() == 0) {
                relation.setLearning_status(scoreDto.getLearning_status());
                UserWordRelation saved = userWordRelationRepository.save(relation);
                results.add(userWordRelationMapper.toDto(saved));
            }

            // 如果状态为1且复习次数没到三次，说明一轮复习还未完成，不更新状态
            else if (scoreDto.getReview_status() != 3 && relation.getStatus() == 1) {
                relation.setReview_status(scoreDto.getReview_status());
                UserWordRelation saved = userWordRelationRepository.save(relation);
                results.add(userWordRelationMapper.toDto(saved));
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
                relation.setLearning_status(0); // 重置学习状态
                relation.setReview_status(0);   // 重置复习状态

                LocalDateTime now = LocalDateTime.now();
                relation.setLastPracticeDate(now);
                relation.setNextPracticeDate(now.plusDays(result.interval));

                // 4. 保存并转换返回
                UserWordRelation saved = userWordRelationRepository.save(relation);
                results.add(userWordRelationMapper.toDto(saved));
            }
        }

        return results; // 记得返回结果，给前端更新 UI
    }
}
