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
@Table(name = "word_classification")
@Schema(description = "词条分类实体")
public class WordClassification {

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

    /** JLPT 分级 */
    @Column(name = "jlpt_levels", length = 50)
    private String jlptLevels;

    /** 教材套系列表 */
    @Column(name = "textbook_sets", length = 255)
    private String textbookSets;

    /** 教材课次列表 */
    @Column(name = "textbook_lessons", length = 500)
    private String textbookLessons;

    /** 频度标签 */
    @Column(name = "frequency_tags", length = 100)
    private String frequencyTags;

    /** 是否常用 */
    @Column(name = "is_common")
    private Boolean isCommon;

    /** 是否语法项 */
    @Column(name = "is_grammar")
    private Boolean isGrammar;

    /** 是否表达项 */
    @Column(name = "is_expression")
    private Boolean isExpression;

    /** 是否名称项 */
    @Column(name = "is_name")
    private Boolean isName;

    /** 关联词条 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
