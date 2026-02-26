package top.sakablog.nichi.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;

import java.util.List;

/**
 * <p>
 * UserBookProgress Entity
 * </p>
 * 存储用户在每本词书中的学习进度，包括已学习的单词数量、正在学习的单词数量等信息。
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="user_book_progress")
public class UserBookProgress {
    // 用户词书进度ID
    @Id
    @NotNull
    @Column(name="user_book_progress_id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    // 用户ID，关联UserProfile实体
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    // 词书ID，关联Book实体
    @Column(name = "book_id", insertable = false, updatable = false)
    private Long bookId;

    // 已学习的单词数量
    @Column(name = "learned_words_count")
    private Integer learnedWordsCount = 0;

    // 正在学习的单词数量
    @Column(name = "learning_words_count")
    private Integer reviewedWordsCount = 0;

    // 是否激活
    @Column(name = "is_active")
    private Boolean isActive = false;

    @OneToMany(mappedBy = "userBookProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordMastery> wordMasteries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

}
