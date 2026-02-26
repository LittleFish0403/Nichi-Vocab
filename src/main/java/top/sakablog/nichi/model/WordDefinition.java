package top.sakablog.nichi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * WordDefinition Entity
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
@Table(name="word_definition")
@Schema(description = "单词详情实体，存储词汇的基础信息之外的补充信息")
public class WordDefinition {
    // 单词ID，作为主键，同时也是与Word表的外键
    @Id
    @NotNull
    @Column(name="word_id", insertable=false, updatable=false)
    private Long id;

    // 词汇的详细定义
    @Column(name="detailed_definition", nullable = true, columnDefinition = "TEXT")
    private String detailedDefinition;

    // 词汇的例句
    @Column(name="example_sentences", nullable = true, columnDefinition = "TEXT")
    private String exampleSentences;

    // 词汇音频
    @Column(name="audio_url", nullable = true)
    private String audioUrl;

    // 关联表
    @OneToOne
    @MapsId
    @JoinColumn(name = "word_id")
    private Word word;
}
