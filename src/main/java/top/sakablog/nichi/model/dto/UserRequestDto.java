package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.User}
 */
@Value
@Schema(description = "用户请求DTO，包含用户注册或登录时需要的基本信息")
public class UserRequestDto implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String username;
    @NotNull
    @NotEmpty
    @NotBlank
    String password;
}