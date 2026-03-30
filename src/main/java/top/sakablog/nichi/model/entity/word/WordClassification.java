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

    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;

    @Column(name = "jlpt_levels", length = 50)
    private String jlptLevels;

    @Column(name = "textbook_sets", length = 255)
    private String textbookSets;

    @Column(name = "textbook_lessons", length = 500)
    private String textbookLessons;

    @Column(name = "frequency_tags", length = 100)
    private String frequencyTags;

    @Column(name = "is_common")
    private Boolean isCommon;

    @Column(name = "is_grammar")
    private Boolean isGrammar;

    @Column(name = "is_expression")
    private Boolean isExpression;

    @Column(name = "is_name")
    private Boolean isName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
