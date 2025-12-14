package top.sakablog.nichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ListWord Entity
 * <p>
 * 存储词汇和词书之间的对应关系。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="list_word")
public class ListWord {
    // 复合主键
    @EmbeddedId
    private ListWordId id;

    // 关联单词表
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @MapsId("wordId")
    private Word word;

    // 关联词书表
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @MapsId("wordBookId")
    private WordBook wordBook;
}
