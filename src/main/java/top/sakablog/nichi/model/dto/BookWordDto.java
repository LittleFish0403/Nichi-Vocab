package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import top.sakablog.nichi.model.BookWord;
import top.sakablog.nichi.model.Word;

import java.io.Serializable;

/**
 * DTO for {@link BookWord}
 */
@Value
@Schema(description = "用于表示词汇与词书对应关系的DTO")
public class BookWordDto implements Serializable {
    BookWordIdDto id;
    Word word;
    BookDto wordBook;
}