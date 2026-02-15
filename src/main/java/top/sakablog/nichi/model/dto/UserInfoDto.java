package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.UserInfo}
 */
@Value
public class UserInfoDto implements Serializable {
    Long user_id;
    Long selectedWordBookId;
}