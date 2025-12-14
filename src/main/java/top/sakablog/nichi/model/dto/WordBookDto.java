package top.sakablog.nichi.model.dto;

import lombok.Value;
import top.sakablog.nichi.model.ListWord;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link top.sakablog.nichi.model.WordBook}
 */
@Value
public class WordBookDto implements Serializable {
    Integer id;
    String name;
    String level;
    Integer count;
    String description;
    List<ListWord> listWords;
}