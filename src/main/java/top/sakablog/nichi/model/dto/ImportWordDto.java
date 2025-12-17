package top.sakablog.nichi.model.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import top.sakablog.nichi.model.Word;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.Word}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportWordDto implements Serializable {
    @CsvBindByName(column = "japanese_word")
    public String japaneseWord;
    @CsvBindByName(column = "kana_reading")
    public String kanaReading;
    @CsvBindByName(column = "meaning_cn")
    public String meaningCn;
    @CsvBindByName(column = "word_type")
    public String wordType;
    @CsvBindByName(column = "source")
    public String source;
}