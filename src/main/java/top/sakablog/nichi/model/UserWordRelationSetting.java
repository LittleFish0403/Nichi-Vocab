package top.sakablog.nichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Table(name="user_word_relation_setting")
public class UserWordRelationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 每次学习的单词数量，默认 15
    private Integer numberOfWordsPerSession;

    // 回传大小, 默认 3
    private Integer feedbackSize;

    // 活动窗口大小，默认 6
    private Integer activeWindowSize;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserWordBookRelation userWordBookRelation;
}
