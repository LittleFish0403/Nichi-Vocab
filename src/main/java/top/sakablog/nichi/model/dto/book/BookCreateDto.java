package top.sakablog.nichi.model.dto.book;

import lombok.Value;
import top.sakablog.nichi.model.entity.Book;

import java.io.Serializable;

/**
 * DTO for {@link Book}
 */
@Value
public class BookCreateDto implements Serializable {
    String name;
    String description;
}