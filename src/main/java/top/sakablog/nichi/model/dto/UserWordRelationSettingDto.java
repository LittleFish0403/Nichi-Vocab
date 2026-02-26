package top.sakablog.nichi.model.dto;

import lombok.Value;
import top.sakablog.nichi.model.StudyConfig;

import java.io.Serializable;

/**
 * DTO for {@link StudyConfig}
 */
@Value
public class UserWordRelationSettingDto implements Serializable {
    Long id;
    Integer numberOfWordsPerSession;
    Integer feedbackSize;
    Integer activeWindowSize;
}