package top.sakablog.nichi.model.dto.word.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Jackson import DTO for entry relation.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WordV2RelationImportDto implements Serializable {
    private String type;
    private String targetEntryId;
    private String targetHeadword;
    private String targetReading;
    private String source;
    private String confidence;
    private String method;
    private String parsedSurface;
    private String parsedPos;
}
