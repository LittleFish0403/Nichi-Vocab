package top.sakablog.nichi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import top.sakablog.nichi.model.UserBookProgress;

import java.io.Serializable;

/**
 * DTO for {@link UserBookProgress}
 */
@Value
@Schema(description = "用户词书进度DTO，包含用户在特定词书中的学习进度信息")
public class UserBookProgressDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long bookId;
    Integer learnedWordsCount;
    Integer reviewedWordsCount;
    Boolean isActive;
}