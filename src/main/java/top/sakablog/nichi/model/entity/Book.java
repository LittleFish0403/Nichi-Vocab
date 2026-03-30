package top.sakablog.nichi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;
import top.sakablog.nichi.model.enums.BookStatus;
import top.sakablog.nichi.model.enums.BookType;
import top.sakablog.nichi.model.enums.BookVisibility;

import java.time.LocalDate;
import java.util.List;

/**
 * Book Entity
 * <p>
 * 存储单词对应的词书。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name="book")
@Schema(description = "词书实体，存储单词对应的词书")
public class Book {
    // 词书ID
    @Id
    @NotNull
    @Column(name="book_id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    // 词书名称
    @Column(name="book_name", nullable = false, unique = true)
    private String name;

    // 词书包含单词数量
    @Column(name="word_count", nullable = false)
    private Integer count;

    // 词书描述
    @Column(name="description")
    private String description = "<NULL>";

    
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private BookType category;

    @Column(name = "cover_url", nullable = false)
    private String coverUrl = "/default/book_cover.png";

    @Column(name = "book_version", nullable = false)
    private String version = "v1.0.0";

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private BookVisibility visibility = BookVisibility.PUBLIC;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.DRAFT;

    @Column(name = "daily_goal", nullable = false)
    private Integer dailyGoal = 20;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "created_time", nullable = false)
    private LocalDate createdTime;

    @Column(name = "updated_time", nullable = false)
    private LocalDate updatedTime;

    // 关联表
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookWord> bookWords;

    @PrePersist
    void onCreate() {
        LocalDate now = LocalDate.now();
        createdTime = now;
        updatedTime = now;
        if (category == null) {
            category = BookType.CUSTOM;
        }
    }

    @PreUpdate
    void onUpdate() {
        updatedTime = LocalDate.now();
    }
}
