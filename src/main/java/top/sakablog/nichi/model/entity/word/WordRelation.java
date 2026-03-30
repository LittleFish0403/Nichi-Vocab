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

    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "target_entry_id", length = 100)
    private String targetEntryId;

    @Column(name = "target_headword", length = 100)
    private String targetHeadword;

    @Column(name = "target_reading", length = 100)
    private String targetReading;

    @Column(name = "source", length = 50)
    private String source;

    @Column(name = "confidence", length = 20)
    private String confidence;

    @Column(name = "parsed_surface", length = 100)
    private String parsedSurface;

    @Column(name = "parsed_pos", length = 50)
    private String parsedPos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
