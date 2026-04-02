package top.sakablog.nichi.model.dto.word;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.WordSourceOccurrence}
 */
@Value
public class WordSourceOccurrenceDto implements Serializable {
    @NotNull
    Long id;
    Long senseId;
    Integer rowIndex;
    Integer lessonNum;
    String lessonLabel;
    String textbookSet;
}