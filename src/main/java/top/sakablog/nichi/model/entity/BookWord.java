package top.sakablog.nichi.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.model.entity.word.Word;

/**
 * BookWord Entity
 * <p>
 * 存储词汇和词书之间的对应关系。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name="book_word")
@Schema(description = "存储词汇和词书之间的对应关系")
public class BookWord {
    // 复合主键
    @EmbeddedId
    private BookWordId id;

    // 关联单词表
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", insertable = false, updatable = false)
    @MapsId("wordId")
    private Word word;

    // 关联词书表
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    @MapsId("bookId")
    private Book book;
}
