package top.sakablog.nichi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.User}
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