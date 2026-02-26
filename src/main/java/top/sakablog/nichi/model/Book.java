package top.sakablog.nichi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;

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

    // 词书等级
    @Column(name="level", nullable = true)
    private String level;

    // 词书包含单词数量
    @Column(name="word_count", nullable = false)
    private Integer count;

    // 词书描述
    @Column(name="description")
    private String description;

    // 关联表
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookWord> bookWords;
}
