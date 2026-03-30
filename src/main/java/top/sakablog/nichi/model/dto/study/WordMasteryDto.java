package top.sakablog.nichi.model.dto.study;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import top.sakablog.nichi.model.entity.study.WordMastery;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link WordMastery}
 */
@Value
@Schema(description = "单词掌握DTO，包含用户对特定单词的掌握情况和下次练习时间等信息")
public class WordMasteryDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long wordId;
    Integer reps;
    Double easiness;
    Integer nextInterval;
    LocalDateTime nextPracticeDate;
    Integer status;
    Integer learnStatus;
    Integer reviewStatus;
}