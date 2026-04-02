package top.sakablog.nichi.model.dto.word;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.WordRelation}
 */
@Value
public class WordRelationDto implements Serializable {
    @NotNull
    Long id;
    Long wordId;
    String type;
    String targetEntryId;
    String targetHeadword;
    String targetReading;
    String source;
    String confidence;
    String parsedSurface;
    String parsedPos;
}