package top.sakablog.nichi.model.enums;

import lombok.Getter;
import java.util.Arrays;

/**
 * WordType
 * <p>
 * 词性枚举类
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Getter
public enum WordType {
    NOUN_PROPER("专有词", "专有名词", "PROPER_NOUN"),
    NOUN_COMMON("名词", "名", "NOUN", "N"),
    VERB_1("动词1", "一类动词", "1类动词", "V1"),
    VERB_2("动词2", "二类动词", "2类动词", "V2"),
    VERB_3("动词3", "三类动词", "3类动词", "V3"),
    ADJECTIVE("形容词", "形", "ADJ"),
    ADVERB("副词", "副", "ADV"),
    PRONOUN("代词", "代", "PRON"),
    PREPOSITION("介词", "介", "PREP"),
    CONJUNCTION("连接词", "连", "CONJ"),
    INTERJECTION("感叹词", "叹", "INTJ"),
    IDIOMATIC_EXPRESSION("惯用语", "短语", "惯用");

    // 存放所有可能的中文/英文别名
    private final String[] aliases;

    WordType(String... aliases) {
        this.aliases = aliases;
    }

    /**
     * 核心转换逻辑：根据字符串匹配枚举
     * @param text 传入的中文、英文或缩写
     * @return 匹配到的枚举，若无匹配则返回 NOUN_COMMON 或抛出异常
     */
    public static WordType fromString(String text) {
        if (text == null || text.isBlank()) {
            return NOUN_COMMON; // 默认值
        }

        String normalized = text.trim().toUpperCase();

        return Arrays.stream(WordType.values())
                .filter(type -> type.name().equals(normalized) ||
                        Arrays.asList(type.aliases).contains(normalized))
                .findFirst()
                .orElseGet(() -> {
                    // 针对特殊的逻辑处理：比如“动词”默认归为 VERB_1
                    if (normalized.contains("动词")) return VERB_1;
                    return NOUN_COMMON; // 默认
                });
    }

    /**
     * 将枚举转换为其主要的字符串表示形式（第一个别名）
     * @return 主要的字符串表示形式
     */
    public String toStringWordType() {
        return this.aliases[0];
    }
}
