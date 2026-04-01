package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.WordRelation;

/**
 * Repository for {@link WordRelation}.
 */
@Repository
public interface WordRelationRepository extends JpaRepository<WordRelation, Long> {
}
