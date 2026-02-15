package top.sakablog.nichi.service;

import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.UserWordBookRelation;
import top.sakablog.nichi.model.UserWordRelation;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.UserWordRelationDto;
import top.sakablog.nichi.model.dto.UserWordRelationScoreDto;
import top.sakablog.nichi.model.dto.WordDto;

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
public interface UserWordLearningService {
    /**
     * 获取待学习单词：顺序抽取，单词池大小
     */
    public List<UserWordRelationDto> getLearningWordsByOrder(Long userWordBookRelationId);

    /**
     * 获取待学习单词：顺序抽取,回传大小
     */
    public List<UserWordRelationDto> getLearningWordsByOrderAdditional(Long userWordBookRelationId);

    /**
     * 获取待学习单词：顺序抽取,指定数量
     */
    public List<WordDto> getLearningWordsByOrder(Long userWordBookRelationId, int count);

    /**
     * 获取待学习单词：乱序抽取
     */
    public List<WordDto> getLearningWordsByRandom(Long userWordBookRelationId);

    /**
     * 获取待学习单词：乱序抽取，指定数量
     */
    public List<WordDto> getLearningWordsByRandom(Long userWordBookRelationId, int count);

    /**
     * 获取待学习单词：按单元抽取
     */
    public List<WordDto> getLearningWordsByUnit(Long userWordBookRelationId, String unit);

    /**
     * 更新用户学习单词
     */
    public List<UserWordRelationDto> updateUserLearningWords(List<UserWordRelationScoreDto> scoreDtos, Long UserWordBookRelationId);
}
