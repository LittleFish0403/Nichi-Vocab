package top.sakablog.nichi.service;

import top.sakablog.nichi.model.dto.study.WordMasteryDto;
import top.sakablog.nichi.model.dto.study.WordMasteryScoreDto;

import java.util.List;

/**
 * LearningService
 * <p>
 * 学习服务接口，提供获取待学习单词和更新用户学习单词的功能
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface LearningService {
    /**
     * 获取待学习单词：顺序抽取，单词池大小
     */
    public List<WordMasteryDto> fetchWordsByOrderForSession(Long userBookProgressId, Long userId);

    /**
     * 获取待学习单词：顺序抽取,回传大小
     */
    public List<WordMasteryDto> fetchWordsByOrderForSync(Long userBookProgressId, Long userId);

    /**
     * 获取待学习单词：顺序抽取,指定数量
     */
    public List<WordMasteryDto> fetchWordsByOrderForSession(Long userBookProgressId, int count);

    /**
     * 获取待学习单词：乱序抽取
     */
    public List<WordMasteryDto> fetchWordsByRandomForSession(Long userBookProgressId);

    /**
     * 获取待学习单词：乱序抽取，指定数量
     */
    public List<WordMasteryDto> fetchWordsByRandomForSession(Long userBookProgressId, int count);

    /**
     * 获取待学习单词：按单元抽取
     */
    public List<WordMasteryDto> fetchWordsByUnitForSession(Long userBookProgressId, String unit);

    /**
     * 更新用户学习单词
     */
    public List<WordMasteryDto> updateWordMastery(List<WordMasteryScoreDto> scoreDtos, Long UserBookProgressId);
}
