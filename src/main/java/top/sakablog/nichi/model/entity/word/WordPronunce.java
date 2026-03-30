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

    @Column(name = "reading", length = 100)
    private String reading;

    @Column(name = "source_name", length = 50)
    private String sourceName;

    @Column(name = "source_ref", length = 50)
    private String sourceRef;

    @Column(name = "accent_type", length = 50)
    private String accentType;

    @Column(name = "pattern", length = 255)
    private String pattern;

    @Column(name = "accent_number")
    private Integer accentNumber;

    @Column(name = "drop_after_mora")
    private Integer dropAfterMora;

    @Column(name = "mora_count")
    private Integer moraCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
