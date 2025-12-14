package top.sakablog.nichi.model.dto;

import lombok.Value;
import top.sakablog.nichi.model.Word;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.ListWord}
 */
@Value
public class ListWordDto implements Serializable {
    ListWordIdDto id;
    Word word;
    WordBookDto wordBook;
}