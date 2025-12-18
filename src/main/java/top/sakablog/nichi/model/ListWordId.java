package top.sakablog.nichi.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ListWordId Entity
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
@Table(name="list_word")
public class ListWordId {
    // 词汇ID
    private Long wordId;

    // 词书ID
    private Long wordBookId;
}
