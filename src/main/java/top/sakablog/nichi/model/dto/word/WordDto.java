package top.sakablog.nichi.model.dto.word;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.Word}
 */
@Value
public class WordDto implements Serializable {
    @NotNull
    Long id;
    String entryId;
    String entryType;
    String headword;
    String headwordKana;
    Boolean kanaOnly;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime updatedAt;
    List<WordSenseDto> senses;
    WordClassificationDto classification;
    List<WordPronunciationDto> pronunciations;
    List<WordExampleDto> examples;
    List<WordRelationDto> relations;
}