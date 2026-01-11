package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link top.sakablog.nichi.model.WordBook}
 */
@Value
@Schema(description = "词书DTO，包含词书的详细信息和关联的单词列表")
public class WordBookDto implements Serializable {
    Long id;
    String name;
    String level;
    Integer count;
    String description;

    List<ListWordDto> listWords;
}