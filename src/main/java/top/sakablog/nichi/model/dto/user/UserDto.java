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
@Schema(description = "用户DTO，包含用户的详细信息")
public class UserDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    String username;
    String password;
    String phone;
    String email;
    String avatarUrl;
}