package top.sakablog.nichi.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

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
@Table(name="user_word_book_relation")
public class UserWordBookRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
    @Column(name = "word_book_id", insertable = false, updatable = false)
    private Long wordBookId;

    private Double progress;

    @OneToMany(mappedBy = "userWordBookRelation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWordRelation> userWordRelations;

    @OneToOne(cascade = CascadeType.ALL)
    private UserWordRelationSetting userWordRelationSetting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_book_id")
    private WordBook wordBook;

}
