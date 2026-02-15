package top.sakablog.nichi.service;

import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.UserWordBookRelation;
import top.sakablog.nichi.model.UserWordRelation;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.WordBook;

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
public interface UserWordRelationService {
    /**
     * 初始化用户单词本单词关系
     */
    public List<UserWordRelation> initUserWordRelation(Long UserWordBookRelationId, List<Word> words);
}
