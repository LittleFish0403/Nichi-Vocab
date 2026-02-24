package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.UserWordRelation;
import top.sakablog.nichi.model.Word;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Repository
public interface UserWordRelationRepository extends JpaRepository<UserWordRelation, Long> {
    @Query(value = """
        SELECT w.* FROM word w
        JOIN list_word lw ON w.id = lw.word_id
        WHERE lw.word_book_id = :wordBookId
        AND w.id NOT IN (
            SELECT uwr.word_id FROM user_word_relation uwr
            WHERE uwr.user_word_book_relation_id = :uwrId
        )
        ORDER BY w.id ASC
        LIMIT :limit
        """, nativeQuery = true)
    List<Word> findNewWordsByOrder(@Param("wordBookId") Long wordBookId,
                                   @Param("uwrId") Long uwrId,
                                   @Param("limit") Integer limit);

    @Query("""
            SELECT w FROM Word w JOIN UserWordRelation uwr ON w.id = uwr.wordId
            WHERE uwr.userWordBookRelation.id = :uwrId
            AND uwr.status = 0
            """)
    List<Word> findLearningWords(Long uwrId);

    Optional<UserWordRelation> findByUserWordBookRelationIdAndWordId(Long userWordBookRelationId, Long wordId);


    @Query("""
            SELECT uwr FROM UserWordRelation uwr
            WHERE uwr.userWordBookRelation.id = :uwrId
            AND uwr.status = 0
            order by uwr.id
            LIMIT :limit
            """)
    List<UserWordRelation> findLearningUserWordRelations(Long uwrId, Integer limit);
}