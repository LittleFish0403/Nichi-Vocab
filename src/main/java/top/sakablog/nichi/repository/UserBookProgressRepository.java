package top.sakablog.nichi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.sakablog.nichi.model.UserBookProgress;
import top.sakablog.nichi.model.Book;

import java.util.List;

/**
 * <p>
 * UserBookProgressRepository
 * </p>
 * UserBookProgress的数据访问层接口，提供对UserBookProgress实体的CRUD操作
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserBookProgressRepository extends JpaRepository<UserBookProgress, Long> {
    @Transactional
    @Modifying
    void deleteByUserIdAndBookId(Long userId, Long bookId);

    @Transactional
    UserBookProgress findByUserIdAndBookId(Long userId, Long bookId);

    @Transactional
    boolean existsUserWordBookRelationByUserIdAndBookId(Long userId, Long bookId);

    // 查询用户词书进度记录中是否存在特定单词的学习关系
    @Query(value =
        """
        SELECT COUNT(*) > 0
        FROM user_book_progress ubp
        JOIN word_mastery wm ON ubp.book_id = wm.book_id
        WHERE ubp.id = :userBookProgressId
          AND wm.word_id = :wordId
        """, nativeQuery = true)
    boolean existsUserWordRelationByUserWordBookRelationIdAndWordId(Long userBookProgressId, Long wordId);

}
