package top.sakablog.nichi.model.dto.word;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import top.sakablog.nichi.model.entity.word.WordSourceOccurrence;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.WordSense}
 */
@Value
public class WordSenseDto implements Serializable {
    @NotNull
    Long id;
    Long wordId;
    String senseId;
    String source;
    String sourceRef;
    String pos;
    String posRaw;
    String glossZhHans;
    String glossEn;
    WordSourceOccurrenceDto sourceOccurrence;
}