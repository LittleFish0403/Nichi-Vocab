package top.sakablog.nichi.model.entity.word;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "word_example")
@Schema(description = "词条例句实体")
public class WordExample {

    /** 主键 ID */
    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 关联词条 ID */
    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;

    /** 日文例句 */
    @Column(name = "ja", columnDefinition = "TEXT")
    private String ja;

    /** 简中译文 */
    @Column(name = "zh_hans", columnDefinition = "TEXT")
    private String zhHans;

    /** 课次编号 */
    @Column(name = "lesson_num")
    private Integer lessonNum;

    /** 课次标签 */
    @Column(name = "lesson_label", length = 100)
    private String lessonLabel;

    /** 教材套系 */
    @Column(name = "textbook_set", length = 100)
    private String textbookSet;

    /** 所属分段 */
    @Column(name = "section", length = 50)
    private String section;

    /** 例句来源 */
    @Column(name = "source", length = 50)
    private String source;

    /** 关联词条 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
