package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link top.sakablog.nichi.model.UserWordRelation}
 */
@Value
public class UserWordRelationDto implements Serializable {
    Long id;
    Long wordId;
    Integer nextInterval;
    LocalDateTime nextPracticeDate;
    Integer status;
    Integer learning_status;
    Integer review_status;
}