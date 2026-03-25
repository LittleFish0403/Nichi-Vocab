package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import top.sakablog.nichi.model.enums.IdentityType;

import java.io.Serializable;

/**
 * DTO for user registration.
 */
@Value
@Schema(description = "用户注册DTO，包含基础用户信息与认证信息")
public class UserRegisterDto implements Serializable {
    @NotBlank
    @Schema(description = "用户名", example = "sakana")
    String username;

    @NotNull
    @Schema(description = "认证标识类型", example = "EMAIL")
    IdentityType identityType;

    @NotBlank
    @Schema(description = "唯一登录标识", example = "sakana@qq.com")
    String identifier;

    @NotBlank
    @Schema(description = "登录凭证，当前为原始密码，入库前应加密", example = "123456")
    String credential;

    @NotBlank
    @Schema(description = "验证码", example = "123456")
    String verifyCode;
}
