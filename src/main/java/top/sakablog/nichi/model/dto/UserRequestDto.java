package top.sakablog.nichi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.User}
 */
@Value
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