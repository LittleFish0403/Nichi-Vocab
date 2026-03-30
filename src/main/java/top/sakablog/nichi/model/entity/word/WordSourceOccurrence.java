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

    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "sense_id", insertable = false, updatable = false)
    private Long senseId;

    @Column(name = "row_index")
    private Integer rowIndex;

    @Column(name = "lesson_num")
    private Integer lessonNum;

    @Column(name = "lesson_label", length = 100)
    private String lessonLabel;

    @Column(name = "textbook_set", length = 100)
    private String textbookSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sense_id")
    private WordSense sense;
}
