package top.sakablog.nichi.service;

import top.sakablog.nichi.model.UserWordBookRelation;
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
public interface UserWordBookRelationService {
    /**
     * 用户选择单词本
     */
    public UserWordBookRelation selectWordBook(Long userId, Long wordBookId);

    /**
     * 用户取消选择单词本
     */
    public void unselectWordBook(Long userId, Long wordBookId);

    /**
     * 根据用户ID查询用户选择的单词本关系
     */
    public List<WordBook> getSelectedWordBooksByUserId(Long userId);

    /**
     * 根据ID查找对应的用户单词本关系
     */
    public UserWordBookRelation getUserWordBookRelationById(Long id);

}
