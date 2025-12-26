package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import top.sakablog.nichi.model.Word;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.ListWord}
 */
@Value
@Schema(description = "用于表示词汇与词书对应关系的DTO")
public class ListWordDto implements Serializable {
    ListWordIdDto id;
    Word word;
    WordBookDto wordBook;
}