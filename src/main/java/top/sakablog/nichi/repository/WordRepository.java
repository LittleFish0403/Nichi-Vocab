package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.Word;

import java.util.List;

/**
 * WordRepository
 * <p>
 * 单词数据库操作
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query("SELECT w FROM Word w " +
            "JOIN w.listWords lw " +  // 先关联中间表
            "JOIN lw.wordBook wb " + // 再关联单词本
            "WHERE wb.id = :wordBookId")
    List<Word> findWordsByWordBookId(@Param("wordBookId") Long wordBookId);
}
