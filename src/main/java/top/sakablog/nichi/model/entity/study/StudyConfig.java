package top.sakablog.nichi.model.entity.study;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;

/**
 * <p>
 * StudyConfig Entity
 * </p>
 * 学习配置实体，存储用户的学习配置选项，如每次学习的单词数量、回传大小和活动窗口大小等。
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name="study_config")
public class StudyConfig {
    @Id
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    // 每次学习的单词数量，默认 15
    private Integer numberOfWordsPerSession;

    // 回传大小, 默认 3
    private Integer feedbackSize;

    // 活动窗口大小，默认 6
    private Integer activeWindowSize;
}
