package top.sakablog.nichi.repository.word;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.word.Word;

import java.util.Optional;

/**
 * Repository for {@link Word}.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    @Override
    @EntityGraph(attributePaths = {
            "senses",
            "senses.sourceOccurrence"
    })
    Optional<Word> findById(Long id);
}
