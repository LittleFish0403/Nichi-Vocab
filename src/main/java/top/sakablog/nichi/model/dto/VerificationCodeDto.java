package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for verification code submission.
 */
@Value
@Schema(description = "验证码DTO，用于接收前端传回的六位数字验证码")
public class VerificationCodeDto implements Serializable {
    @NotBlank
    @Schema(description = "临时校验 key", example = "register:email:temp:8f3a2c1d")
    String tempKey;

    @NotBlank
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须为6位数字")
    @Schema(description = "六位数字验证码", example = "123456")
    String code;
}
