package top.sakablog.nichi.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;
import top.sakablog.nichi.config.Snowflake;

import java.time.LocalDateTime;

/**
 * <p>
 * WordMastery Entity
 * </p>
 * 单词掌握度实体，记录用户对每个单词的学习状态和复习计划。
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="word_mastery")
public class WordMastery {
    // 主键ID
    @Id
    @NotNull
    @Column(name="word_mastery_id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    // 单词ID，关联Word实体
    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;

    @Column(name = "user_book_progress_id", insertable = false, updatable = false)
    private Long userBookProgressId;

    // 连续成功次数
    @Column(name = "reps")
    @Comment("连续成功次数")
    private Integer reps = 0;

    // 上一次复习的质量评分（0-5）
    @Column(name = "easiness")
    @Comment("简单因子（SM-2算法核心）")
    private Double easiness = 2.5; // SM-2 算法默认起始值

    // 上一次复习的间隔（天数）
    @Column(name = "last_interval")
    @Comment("上一次复习间隔(天)")
    private Integer lastInterval = 0;

    // 下一次复习的间隔（天数）
    @Column(name = "next_interval")
    @Comment("下一次复习间隔(天)")
    private Integer nextInterval = 0;

    // 上一次练习的日期
    @Column(name = "last_practice_date")
    private LocalDateTime lastPracticeDate;

    // 下一次练习的日期
    @Column(name = "next_practice_date")
    private LocalDateTime nextPracticeDate;

    // 学习状态：0-未学习，1-学习中，2-已掌握
    @Column(name = "status")
    private Integer status = 0;

    // 循环学习次数
    @Column(name = "learn_status")
    private Integer learnStatus = 0;

    // 循环复习次数
    @Column(name = "review_status")
    private Integer reviewStatus = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_book_progress_id")
    private UserBookProgress userBookProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
