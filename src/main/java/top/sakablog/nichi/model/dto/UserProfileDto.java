package top.sakablog.nichi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.sakablog.nichi.model.UserProfile;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link UserProfile}
 */
@Data
@Schema(description = "用户配置DTO，包含用户的学习配置和选择的词书信息")
public class UserProfileDto implements Serializable {
    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long selectedBookId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long selectedUserBookProgressId;
    Integer numberOfWordsPerSession;
    Integer feedbackSize;
    Integer activeWindowSize;
    LocalDateTime lastLoginTime;
    Boolean isLoggedIn;
}