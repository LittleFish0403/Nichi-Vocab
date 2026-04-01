package top.sakablog.nichi.model.dto.word.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Jackson import DTO for source occurrence.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WordV2SourceOccurrenceImportDto implements Serializable {
    private String source;
    private Integer rowIndex;
    private Integer lessonNum;
    private String lessonLabel;
    private String textbookSet;
    private String wordSurface;
    private String reading;
    private String posRaw;
    private String glossZhHans;
    private List<Double> audioRange;
}
