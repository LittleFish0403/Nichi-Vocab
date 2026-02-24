package top.sakablog.nichi.model.dto;

import lombok.Data;
import top.sakablog.nichi.model.UserProfile;

import java.io.Serializable;

/**
 * DTO for {@link UserProfile}
 */
@Data
public class UserInfoDto implements Serializable {
    Long userId;
    Long selectedWordBookId;
}