package top.sakablog.nichi.model.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.model.entity.study.UserBookProgress;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserProfile Entity
 * <p>
 * 存储用户的学习配置和选择的词书信息。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.2
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name="user_profile")
public class UserProfile {
    // 用户ID，与User实体的ID相同
    @Id
    @NotNull
    @Column(name="user_id", insertable=false, updatable=false)
    private Long id;

    // 用户选择的单词书ID，关联Book实体
    @Column(name="selected_book_id", insertable=false, updatable=false)
    private Long selectedBookId;

    @Column(name="selected_user_book_progress_id", insertable=false, updatable=false)
    private Long selectedUserBookProgressId;

    // 每次学习的单词数量，默认 15
    @Column(name="number_of_words_per_session")
    private Integer numberOfWordsPerSession = 15;

    // 回传大小, 默认 3
    @Column(name="feedback_size")
    private Integer feedbackSize = 5;

    // 活动窗口大小，默认 6
    @Column(name="active_window_size")
    private Integer activeWindowSize = 6;

    //登陆时间
    @Column(name="last_login_time")
    private LocalDateTime lastLoginTime;

    //登录状态
    @Column(name="is_logged_in")
    private Boolean isLoggedIn = false;

    // 关联表
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "selected_book_id")
    private Book selectedBook;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "selected_user_book_progress_id")
    private UserBookProgress selectedUserBookProgress;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserBookProgress> userBookProgresses;
}
