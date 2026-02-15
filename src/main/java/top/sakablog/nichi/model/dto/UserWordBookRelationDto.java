package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.UserWordBookRelation}
 */
@Value
public class UserWordBookRelationDto implements Serializable {
    Long id;
    Long userId;
    Long wordBookId;
    Double progress;
}