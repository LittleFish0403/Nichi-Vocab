package top.sakablog.nichi.model.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import top.sakablog.nichi.model.Word;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.Word}
 */
@Value
@Getter
@Setter
public class ImportWordDto implements Serializable {
    @CsvBindByName(column = "日文")
    String japaneseWord;
    @CsvBindByName(column = "假名")
    String kanaReading;
    @CsvBindByName(column = "中文")
    String meaningCn;
    @CsvBindByName(column = "类型")
    Word.WordType wordType;
    @CsvBindByName(column = "来源")
    String source;


}