package top.sakablog.nichi.model.dto.word;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.WordClassification}
 */
@Value
public class WordClassificationDto implements Serializable {
    @NotNull
    Long id;
    Long wordId;
    String jlptLevels;
    String textbookSets;
    String textbookLessons;
    String frequencyTags;
    Boolean isCommon;
    Boolean isGrammar;
    Boolean isExpression;
    Boolean isName;
}