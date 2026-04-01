package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.Word;

/**
 * Repository for {@link Word}.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
}
