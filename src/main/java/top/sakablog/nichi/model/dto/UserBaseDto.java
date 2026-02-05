package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.User}
 */
@Value
public class UserBaseDto implements Serializable {
    Long user_id;
    String username;
    String avatarUrl;
}