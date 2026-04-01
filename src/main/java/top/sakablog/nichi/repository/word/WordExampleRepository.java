package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.WordExample;

/**
 * Repository for {@link WordExample}.
 */
@Repository
public interface WordExampleRepository extends JpaRepository<WordExample, Long> {
}
