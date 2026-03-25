package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import top.sakablog.nichi.model.enums.IdentityType;

import java.io.Serializable;

/**
 * DTO for verification code request.
 */
@Value
@Schema(description = "验证码请求DTO，用于接收验证码发送目标信息")
public class VerifyCodeRequestDto implements Serializable {
    @NotNull
    @Schema(description = "认证类型", example = "EMAIL")
    IdentityType identityType;

    @NotBlank
    @Schema(description = "手机号或邮箱", example = "sakana@qq.com")
    String identifier;
}
