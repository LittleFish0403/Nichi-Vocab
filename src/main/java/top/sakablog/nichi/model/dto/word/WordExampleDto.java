package top.sakablog.nichi.model.dto.word;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.WordExample}
 */
@Value
public class WordExampleDto implements Serializable {
    @NotNull
    Long id;
    Long wordId;
    String ja;
    String zhHans;
    Integer lessonNum;
    String lessonLabel;
    String textbookSet;
    String section;
    String source;
}