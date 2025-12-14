package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.sakablog.nichi.model.ListWord;

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
public interface ListWordRepository extends JpaRepository<ListWord, Integer> {
    List<ListWord> findListWordByWordBookId(Integer wordBookId);

    void saveListWord(ListWord listWord);
    List<ListWord> saveAllListWord(List<ListWord> listWords);
}
