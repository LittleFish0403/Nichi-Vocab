package top.sakablog.nichi.model.dto.word.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Jackson import DTO for accent payload.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WordV2AccentImportDto implements Serializable {
    private String system;
    private Integer accentNumber;
    private String accentType;
    private List<String> pattern;
    private Integer dropAfterMora;
    private Integer moraCount;
    private String source;
    private String confidence;
}
