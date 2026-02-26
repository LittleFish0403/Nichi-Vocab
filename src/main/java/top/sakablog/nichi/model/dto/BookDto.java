package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import top.sakablog.nichi.model.Book;

import java.io.Serializable;

/**
 * DTO for {@link Book}
 */
@Value
@Schema(description = "词书DTO，包含词书的详细信息和关联的单词列表")
public class BookDto implements Serializable {
    Long id;
    String name;
    String level;
    Integer count;
    String description;
}