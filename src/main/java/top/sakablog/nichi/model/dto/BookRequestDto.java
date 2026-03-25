package top.sakablog.nichi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.Book}
 */
@Value
public class BookRequestDto implements Serializable {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    String name;
    String level;
    Integer count;
    String description;
}