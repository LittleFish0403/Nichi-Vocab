package top.sakablog.nichi.service;

import top.sakablog.nichi.model.UserBookProgress;
import top.sakablog.nichi.model.Book;

import java.util.List;

/**
 * UserBookProgressService
 * <p>
 * 用户单词本进度服务接口
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserBookProgressService {
    /**
     * 用户选择单词本
     */
    public UserBookProgress createUserBookProgress(Long userId, Long bookId);

    /**
     * 用户取消选择单词本
     */
    public void deleteUserBookProgress(Long userId, Long bookId);

    /**
     * 根据用户ID查询用户选择的单词本关系
     */
    public List<Book> getSelectedBooksByUserId(Long userId);

    /**
     * 根据ID查找对应的用户单词本关系
     */
    public UserBookProgress findUserBookProgressById(Long id);

    /**
     * 根据用户ID和单词本ID查找对应的用户单词本关系
     */
    public UserBookProgress getUserBookProgressByUserIdAndBookId(Long userId, Long bookId);

}
