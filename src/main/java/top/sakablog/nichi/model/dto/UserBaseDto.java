package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.User}
 */
@Value
@Schema(description = "用户基本信息DTO，包含用户ID、用户名和头像URL")
public class UserBaseDto implements Serializable {
    Long id;
    String username;
    String avatarUrl;
}