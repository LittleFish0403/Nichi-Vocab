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
@Table(name = "word_pronunce")
@Schema(description = "词条发音实体")
public class WordPronunce {

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

    /** 发音类型 */
    @Column(name = "type", length = 50)
    private String type;

    /** 发音读法 */
    @Column(name = "reading", length = 100)
    private String reading;

    /** 来源名称 */
    @Column(name = "source_name", length = 50)
    private String sourceName;

    /** 来源内引用 */
    @Column(name = "source_ref", length = 50)
    private String sourceRef;

    /** 重音类型 */
    @Column(name = "accent_type", length = 50)
    private String accentType;

    /** 重音模式 */
    @Column(name = "pattern", length = 255)
    private String pattern;

    /** 重音编号 */
    @Column(name = "accent_number")
    private Integer accentNumber;

    /** 降调后拍位 */
    @Column(name = "drop_after_mora")
    private Integer dropAfterMora;

    /** 总拍数 */
    @Column(name = "mora_count")
    private Integer moraCount;

    /** 关联词条 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
