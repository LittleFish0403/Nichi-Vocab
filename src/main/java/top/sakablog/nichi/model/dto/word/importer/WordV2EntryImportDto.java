package top.sakablog.nichi.model.dto.word.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Jackson import DTO for a V2 word entry.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WordV2EntryImportDto implements Serializable {
    private String entryId;
    private String entryType;
    private String headword;
    private String headwordKana;
    private Boolean kanaOnly;
    private List<WordV2OrthographyImportDto> orthographies;
    private List<WordV2SenseImportDto> senses;
    private List<String> sourceTags;
    private WordV2ClassificationImportDto classifications;
    private List<WordV2SourceOccurrenceImportDto> sourceOccurrences;
    private WordV2StudyMetaImportDto studyMeta;
    private List<WordV2RelationImportDto> relations;
    private List<WordV2PronunciationImportDto> pronunciations;
    private List<WordV2ExampleImportDto> examples;
}
