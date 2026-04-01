package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.WordClassification;

/**
 * Repository for {@link WordClassification}.
 */
@Repository
public interface WordClassificationRepository extends JpaRepository<WordClassification, Long> {
}
