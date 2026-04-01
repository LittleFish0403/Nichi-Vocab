package top.sakablog.nichi.model.dto.word.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Jackson import DTO for pronunciation.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WordV2PronunciationImportDto implements Serializable {
    private String type;
    private String reading;
    private String sourceName;
    private String sourceRef;
    private WordV2AccentImportDto accent;
}
