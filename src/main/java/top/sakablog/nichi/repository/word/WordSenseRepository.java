package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.WordSense;

/**
 * Repository for {@link WordSense}.
 */
@Repository
public interface WordSenseRepository extends JpaRepository<WordSense, Long> {
}
