package top.sakablog.nichi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.model.enums.WordType;

import java.util.Arrays;
import java.util.List;

/**
 * Word Entity
 * <p>
 * 存储词汇的基础信息、发音和词性。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="word")
@Schema(description = "单词实体，存储词汇的基础信息、发音和词性")
public class Word {
    // 单词ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 日语单词
    @Column(name="japanese_word", nullable = false)
    private String japaneseWord;

    // 假名读音
    @Column(name="kana_reading", nullable = false)
    private String kanaReading;

    // 中文含义
    @Column(name="meaning_cn", nullable = false)
    private String meaningCn;

    // 词性
    @Enumerated(EnumType.STRING)
    @Column(name="word_type", nullable = false)
    private WordType wordType;

    @Column(nullable = true)
    private String source;

    // 关联表
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListWord> listWords;
}
