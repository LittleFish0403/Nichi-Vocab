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

    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;

    @Column(name = "ja", columnDefinition = "TEXT")
    private String ja;

    @Column(name = "zh_hans", columnDefinition = "TEXT")
    private String zhHans;

    @Column(name = "lesson_num")
    private Integer lessonNum;

    @Column(name = "lesson_label", length = 100)
    private String lessonLabel;

    @Column(name = "textbook_set", length = 100)
    private String textbookSet;

    @Column(name = "section", length = 50)
    private String section;

    @Column(name = "source", length = 50)
    private String source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
