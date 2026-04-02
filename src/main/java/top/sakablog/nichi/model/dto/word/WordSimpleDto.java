package top.sakablog.nichi.model.dto.word;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.entity.word.Word}
 */
@Value
public class WordSimpleDto implements Serializable {
    @NotNull
    Long id;
    String entryId;
    String entryType;
    String headword;
    String headwordKana;
}