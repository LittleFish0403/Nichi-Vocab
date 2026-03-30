package top.sakablog.nichi.service;

import top.sakablog.nichi.model.entity.study.WordMastery;
import top.sakablog.nichi.model.entity.word.Word;

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
public interface WordMasteryService {
    /**
     * 初始化用户单词本单词关系
     */
    public List<WordMastery> initWordMasteries(Long UserBookProgressId, List<Word> words);

    /**
     * 根据用户单词本关系ID获取用户单词掌握情况
     */
    public List<WordMastery> fetchLearningWordMasteries(Long UserBookProgressId, Integer limit);
}
