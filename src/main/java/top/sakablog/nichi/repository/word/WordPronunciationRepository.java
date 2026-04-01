package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.WordPronunciation;

/**
 * Repository for {@link WordPronunciation}.
 */
@Repository
public interface WordPronunciationRepository extends JpaRepository<WordPronunciation, Long> {
}
