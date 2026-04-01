package top.sakablog.nichi.model.dto.word.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Jackson import DTO for example audio.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WordV2ExampleAudioImportDto implements Serializable {
    private Integer lessonNum;
    private Integer audioType;
    private Double start;
    private Double end;
}
