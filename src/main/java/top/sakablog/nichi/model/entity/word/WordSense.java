package top.sakablog.nichi.model.entity.word;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "word_sense")
@Schema(description = "词义项实体")
public class WordSense {

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

    /** 词义唯一标识 */
    @Column(name = "sense_id", nullable = false, length = 100)
    private String senseId;

    /** 词义来源 */
    @Column(name = "source", length = 50)
    private String source;

    /** 来源内引用 */
    @Column(name = "source_ref", length = 50)
    private String sourceRef;

    /** 词性 */
    @Column(name = "pos", length = 50)
    private String pos;

    /** 原始词性文本 */
    @Column(name = "pos_raw", length = 50)
    private String posRaw;

    /** 简中释义 */
    @Column(name = "gloss_zh_hans", length = 500)
    private String glossZhHans;

    /** 英文释义 */
    @Column(name = "gloss_en", length = 500)
    private String glossEn;

    /** 关联词条 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    /** 关联来源出现记录 */
    @OneToMany(mappedBy = "sense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordSourceOccurrence> sourceOccurrences;
}
