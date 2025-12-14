package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.sakablog.nichi.model.Word;

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
public interface WordRepository extends JpaRepository<Word, Integer> {
    List<Word> findWordById(String id);

    List<Word> saveAllWords(List<Word> words);
}
