package top.sakablog.nichi.model.dto;

import com.opencsv.bean.CsvBindByName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import top.sakablog.nichi.model.Word;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.Word}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用于导入单词的DTO")
public class ImportWordDto implements Serializable {
    @CsvBindByName(column = "japanese_word", required = true)
    public String japaneseWord;
    @CsvBindByName(column = "kana_reading", required = true)
    public String kanaReading;
    @CsvBindByName(column = "meaning_cn", required = true)
    public String meaningCn;
    @CsvBindByName(column = "word_type")
    public String wordType;
    @CsvBindByName(column = "source")
    public String source;
}