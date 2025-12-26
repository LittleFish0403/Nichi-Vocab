package top.sakablog.nichi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * WordDetail Entity
 * <p>
 * 存储词汇的基础信息之外的补充信息。
 * TODO：还未编辑完成
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="word_detail")
@Schema(description = "单词详情实体，存储词汇的基础信息之外的补充信息")
public class WordDetail {
    @Id
    private Long id;
    private String detailed_notes;
    private String pitch_accent;
}
