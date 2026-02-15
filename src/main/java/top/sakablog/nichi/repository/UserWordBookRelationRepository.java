package top.sakablog.nichi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
public interface UserWordBookRelationRepository extends JpaRepository<UserWordBookRelation, Long> {
    @Transactional // 必须：删除操作必须在事务内
    @Modifying // 建议：明确标识这是一个修改操作
    void deleteUserWordBookRelationByUserIdAndWordBookId(Long userId, Long wordBookId);

    @Transactional
    UserWordBookRelation findUserWordBookRelationByUserIdAndWordBookId(Long userId, Long wordBookId);

    @Transactional
    boolean existsUserWordBookRelationByUserIdAndWordBookId(Long userId, Long wordBookId);

    @Query(value =
        """
        SELECT COUNT(*) > 0
        FROM user_wordbook_relation uwbr
        JOIN word_book_realtion wbr ON uwbr.wordbook_id = wbr.wordbook_id
        WHERE uwbr.id = :userWordBookRelationId
          AND wbw.word_id = :wordId
        """, nativeQuery = true)
    boolean existsUserWordRelationByUserWordBookRelationIdAndWordId(Long userWordBookRelationId, Long wordId);

    List<WordBook> findByUserId(Long userId);
}
