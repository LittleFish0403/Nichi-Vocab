package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface WordRepository extends JpaRepository<Word, Integer> {

}
