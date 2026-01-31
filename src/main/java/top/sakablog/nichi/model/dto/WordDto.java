package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.enums.WordType;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link top.sakablog.nichi.model.Word}
 */
@Value
@Schema(description = "单词DTO，包含单词的详细信息和关联的词书列表")
public class WordDto implements Serializable {
    @NotNull
    Long id;
    @NotNull
    String japaneseWord;
    @NotNull
    String kanaReading;
    @NotNull
    String meaningCn;
    String wordType;
    String source;
}