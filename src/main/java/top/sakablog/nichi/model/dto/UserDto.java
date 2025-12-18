package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.User}
 */
@Value
public class UserDto implements Serializable {
    Long user_id;
    String username;
    String password;
    String email;
    String phone;
    String avatarUrl;
}