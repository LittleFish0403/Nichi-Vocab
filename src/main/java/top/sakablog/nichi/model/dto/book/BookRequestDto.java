package top.sakablog.nichi.model.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.model.enums.BookStatus;
import top.sakablog.nichi.model.enums.BookType;
import top.sakablog.nichi.model.enums.BookVisibility;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Book}
 */
@Value
public class BookRequestDto implements Serializable {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    String name;
    Integer count;
    String description;
    BookType category;
    String coverUrl;
    String version;
    BookVisibility visibility;
    BookStatus status;
    Integer dailyGoal;
    Integer sortOrder;
    LocalDate createdTime;
    LocalDate updatedTime;
}
