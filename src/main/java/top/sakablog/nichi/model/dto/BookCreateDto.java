package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.Book}
 */
@Value
public class BookCreateDto implements Serializable {
    String name;
    String description;
}