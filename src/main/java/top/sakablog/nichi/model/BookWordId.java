package top.sakablog.nichi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * BookWordId Entity
 * <p>
 * ListWord关联表的ID类，用于复合主键映射。
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */

@Data
@Accessors(chain = true)
@Embeddable
@NoArgsConstructor
@Schema(description = "ListWord关联表的ID类，用于复合主键映射")
public class BookWordId implements java.io.Serializable {
    // 词汇ID
    @Column(name = "word_id")
    private Long wordId;

    // 词书ID
    @Column(name = "book_id")
    private Long bookId;
}
