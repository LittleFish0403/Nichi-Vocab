package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.WordSourceOccurrence;

/**
 * Repository for {@link WordSourceOccurrence}.
 */
@Repository
public interface WordSourceOccurrenceRepository extends JpaRepository<WordSourceOccurrence, Long> {
}
