package top.sakablog.nichi.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="user_word_relation")
public class UserWordRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;
    private Integer reps;
    private Double easiness;
    private Integer lastInterval;
    private Integer nextInterval;
    private LocalDateTime lastPracticeDate;
    private LocalDateTime nextPracticeDate;
    private Integer status; // 0：学习中 1：已学习，未掌握 2：已掌握
    private Integer learning_status; // 学习时循环次数，每次循环结束后增加1，达到一定次数后才会进入下一个阶段
    private Integer review_status; // 复习时循环次数，每次循环结束后增加1，达到一定次数后才会进入下一个阶段

    @ManyToOne(fetch = FetchType.LAZY)
    private UserWordBookRelation userWordBookRelation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
