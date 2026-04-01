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
@Table(name = "word_source_occurrence")
@Schema(description = "词义来源出现记录实体")
public class WordSourceOccurrence {

    /** 主键 ID */
    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 关联词义 ID */
    @Column(name = "sense_id", insertable = false, updatable = false)
    private Long senseId;

    /** 原始行序号 */
    @Column(name = "row_index")
    private Integer rowIndex;

    /** 课次编号 */
    @Column(name = "lesson_num")
    private Integer lessonNum;

    /** 课次标签 */
    @Column(name = "lesson_label", length = 100)
    private String lessonLabel;

    /** 教材套系 */
    @Column(name = "textbook_set", length = 100)
    private String textbookSet;

    /** 关联词义 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sense_id")
    private WordSense sense;
}
