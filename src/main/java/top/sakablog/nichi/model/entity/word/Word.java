package top.sakablog.nichi.model.entity.word;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Collate;
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

    /** 主键 ID */
    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 词条唯一标识 */
    @Column(name = "entry_id", nullable = false, unique = true, length = 100)
    @Collate("utf8mb4_bin")
    private String entryId;

    /** 词条类型 */
    @Column(name = "entry_type", nullable = false, length = 20)
    private String entryType;

    /** 词条表记 */
    @Column(name = "headword", nullable = false, length = 100)
    @Collate("utf8mb4_bin")
    private String headword;

    /** 词条假名 */
    @Column(name = "headword_kana", length = 100)
    @Collate("utf8mb4_bin")
    private String headwordKana;

    /** 是否仅假名 */
    @Column(name = "kana_only", nullable = false)
    private Boolean kanaOnly = false;

    /** 创建时间 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** 关联书词记录 */
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookWord> bookWords;

    /** 关联词义列表 */
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordSense> senses;

    /** 关联分类信息 */
    @OneToOne(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private WordClassification classification;

    /** 关联发音信息 */
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordPronunciation> pronunciations;

    /** 关联例句列表 */
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordExample> examples;

    /** 关联词条关系 */
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
