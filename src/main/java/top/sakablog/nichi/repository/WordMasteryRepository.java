package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.study.WordMastery;
import top.sakablog.nichi.model.entity.word.Word;

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
public interface WordMasteryRepository extends JpaRepository<WordMastery, Long> {

    // 查询用户在特定词书中尚未学习的单词，按照单词ID升序排序，并限制返回数量
    @Query("""
        SELECT w FROM Word w
        JOIN BookWord bw ON w = bw.word
        WHERE bw.book.id = :bookId
        AND w.id NOT IN (
            SELECT wm.wordId FROM WordMastery wm
            WHERE wm.userBookProgressId = :ubpId
        )
        ORDER BY w.id ASC
        LIMIT :limit
        """)
    List<Word> findNewWordsByOrderForLimit(@Param("bookId") Long bookId,
                                           @Param("ubpId") Long ubpId,
                                           @Param("limit") Integer limit);

    Optional<WordMastery> findByUserBookProgressIdAndWordId(Long userBookProgressId, Long wordId);


    // 查询用户在特定词书中正在学习的单词，按照单词ID升序排序，并限制返回数量
    @Query("""
            SELECT wm FROM WordMastery wm
            WHERE wm.userBookProgress.id = :uwrId
            AND wm.status = 0
            order by wm.id
            LIMIT :limit
            """)
    List<WordMastery> findLearningWordMasteries(Long uwrId, Integer limit);
}