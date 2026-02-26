package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.sakablog.nichi.model.UserProfile;

import java.io.Serializable;

/**
 * DTO for {@link UserProfile}
 */
@Data
@Schema(description = "用户配置DTO，包含用户的学习配置和选择的词书信息")
public class UserProfileDto implements Serializable {
    Long id;
    Long selectedWordBookId;
    Integer numberOfWordsPerSession;
    Integer feedbackSize;
    Integer activeWindowSize;
}