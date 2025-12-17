package top.sakablog.nichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * WordList Entity
 * <p>
 * 存储单词对应的词书。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="word_book")
public class WordBook {
    // 词书ID
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

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
    @OneToMany(mappedBy = "wordBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListWord> listWords;
}
