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
@Table(name = "word_relation")
@Schema(description = "词条关联实体")
public class WordRelation {

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

    /** 关系类型 */
    @Column(name = "type", length = 50)
    private String type;

    /** 目标词条标识 */
    @Column(name = "target_entry_id", length = 100)
    private String targetEntryId;

    /** 目标词条表记 */
    @Column(name = "target_headword", length = 100)
    private String targetHeadword;

    /** 目标词条读法 */
    @Column(name = "target_reading", length = 100)
    private String targetReading;

    /** 关系来源 */
    @Column(name = "source", length = 50)
    private String source;

    /** 置信度标记 */
    @Column(name = "confidence", length = 20)
    private String confidence;

    /** 解析表层形 */
    @Column(name = "parsed_surface", length = 100)
    private String parsedSurface;

    /** 解析词性 */
    @Column(name = "parsed_pos", length = 50)
    private String parsedPos;

    /** 关联词条 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
