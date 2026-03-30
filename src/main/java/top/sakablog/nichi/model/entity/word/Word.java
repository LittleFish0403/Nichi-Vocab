package top.sakablog.nichi.model.entity.word;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;
import top.sakablog.nichi.model.entity.BookWord;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Word Entity
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "word")
@Schema(description = "V2 词条主表实体")
public class Word {

    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "entry_id", nullable = false, unique = true, length = 100)
    private String entryId;

    @Column(name = "entry_type", nullable = false, length = 20)
    private String entryType;

    @Column(name = "headword", nullable = false, length = 100)
    private String headword;

    @Column(name = "headword_kana", length = 100)
    private String headwordKana;

    @Column(name = "kana_only", nullable = false)
    private Boolean kanaOnly = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookWord> bookWords;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordSense> senses;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordClassification> classifications;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordPronunce> pronunces;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordExample> examples;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordRelation> relations;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (kanaOnly == null) {
            kanaOnly = false;
        }
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
