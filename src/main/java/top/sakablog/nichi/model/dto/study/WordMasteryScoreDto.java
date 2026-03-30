package top.sakablog.nichi.model.dto.study;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Getter
@Setter
@Schema(description = "单词掌握分数DTO，包含单词掌握ID、分数、学习状态和复习状态")
public class WordMasteryScoreDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long wordMasteryId;
    private Integer score;
    private Integer learnStatus;
    private Integer reviewStatus;
}
