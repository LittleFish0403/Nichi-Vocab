package top.sakablog.nichi.model.dto.word.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Jackson import DTO for example.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WordV2ExampleImportDto implements Serializable {
    private String ja;
    private String zhHans;
    private Integer lessonNum;
    private String lessonLabel;
    private String textbookSet;
    private String section;
    private String source;
    private WordV2ExampleAudioImportDto audio;
}
