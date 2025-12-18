package top.sakablog.nichi.model.dto;

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

    List<ListWordDto> listWords;
}