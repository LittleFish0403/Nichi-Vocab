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

    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;

    @Column(name = "sense_id", nullable = false, length = 100)
    private String senseId;

    @Column(name = "source", length = 50)
    private String source;

    @Column(name = "source_ref", length = 50)
    private String sourceRef;

    @Column(name = "pos", length = 50)
    private String pos;

    @Column(name = "pos_raw", length = 50)
    private String posRaw;

    @Column(name = "gloss_zh_hans", length = 500)
    private String glossZhHans;

    @Column(name = "gloss_en", length = 500)
    private String glossEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    @OneToMany(mappedBy = "sense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordSourceOccurrence> sourceOccurrences;
}
