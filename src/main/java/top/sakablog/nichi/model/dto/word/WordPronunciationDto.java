package top.sakablog.nichi.model.dto.word;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.WordPronunciation}
 */
@Value
public class WordPronunciationDto implements Serializable {
    @NotNull
    Long id;
    Long wordId;
    String type;
    String reading;
    String sourceName;
    String sourceRef;
    String accentType;
    String pattern;
    Integer accentNumber;
    Integer dropAfterMora;
    Integer moraCount;
}