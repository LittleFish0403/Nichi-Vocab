package top.sakablog.nichi.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import top.sakablog.nichi.model.entity.user.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
@Schema(description = "用户基本信息DTO，包含用户ID、用户名和头像URL")
public class UserBaseDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    String username;
    String avatarUrl;
}