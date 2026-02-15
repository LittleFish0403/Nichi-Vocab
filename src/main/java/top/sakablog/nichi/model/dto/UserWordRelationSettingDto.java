package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.UserWordRelationSetting}
 */
@Value
public class UserWordRelationSettingDto implements Serializable {
    Long id;
    Integer numberOfWordsPerSession;
    Integer feedbackSize;
    Integer activeWindowSize;
}