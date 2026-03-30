package top.sakablog.nichi.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import top.sakablog.nichi.model.enums.IdentityType;

import java.io.Serializable;

@Value
@Schema(description = "用户登录 DTO")
public class UserLoginDto implements Serializable {
    @NotNull
    @Schema(description = "认证类型", example = "EMAIL")
    IdentityType identityType;

    @NotBlank
    @Schema(description = "手机号或邮箱", example = "sakana@qq.com")
    String identifier;

    @NotBlank
    @Schema(description = "登录凭证", example = "123456")
    String credential;
}
