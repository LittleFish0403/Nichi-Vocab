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
    // === 名词类 ===
    NOUN("普通名词", "n", "名词", "NOUN"),
    NOUN_PROPER("专有名词", "n-pr", "专有词", "PROPER_NOUN"),
    NOUN_ADV("副词性名词", "n-adv", "时间名词", "N_ADV"),

    // === 代词类 ===
    PRONOUN("代词", "pn", "代名词", "PRON"),

    // === 动词类 (核心) ===
    // 注：国内教材的1类动词 = 五段动词；2类 = 一段；3类 = サ变/カ变
    VERB_GODAN("五段动词", "v5", "1类动词", "一类动词", "动1", "V1"),
    VERB_ICHIDAN("一段动词", "v1", "2类动词", "二类动词", "动2", "V2"),
    VERB_SAHEN("サ变动词", "vs", "3类动词", "三类动词", "动3", "V3"),
    VERB_KAHEN("カ变动词", "vk", "3类动词", "三类动词", "动3", "V3"),
    VERB_UNCLASSIFIED("动词", "v", "动词", "VERB"),

    // === 动词类 (自他属性) ===
    VERB_INTRANSITIVE("自动词", "vi", "自"),
    VERB_TRANSITIVE("他动词", "vt", "他"),

    // === 形容词类 ===
    ADJECTIVE_I("い形容词", "adj-i", "形容词", "形1", "ADJ"),
    ADJECTIVE_NA("な形容词", "adj-na", "形容动词", "形2", "ADJ_NA"),
    ADJECTIVE_NO("の形容词", "adj-no", "名词的"),
    ADJECTIVE_PN("连体词", "adj-pn", "连体"),

    // === 其他独立词类 ===
    ADVERB("副词", "adv", "ADV"),
    CONJUNCTION("接续词", "conj", "连接词", "连", "CONJ"),
    INTERJECTION("感叹词", "int", "叹", "INTJ"),

    // === 附属词及短语 ===
    AFFIX_PRE("前缀", "pref", "接头词"),
    AFFIX_SUF("后缀", "suf", "接尾词"),
    IDIOMATIC_EXPRESSION("惯用语", "exp", "固定表达", "短语", "IDIOM"),

    UNKNOWN("未知", "unk", "未知词性", "UNKNOWN");

    // 存放所有可能的中文/英文别名
    private final String[] aliases;

    WordType(String... aliases) {
        this.aliases = aliases;
    }

    /**
     * 核心转换逻辑：根据字符串匹配枚举
     * @param text 传入的中文、英文或缩写
     * @return 匹配到的枚举，若无匹配则返回 UNKNOWN 或抛出异常
     */
    public static WordType fromString(String text) {
        if (text == null || text.isBlank()) {
            return UNKNOWN; // 默认值
        }

        String normalized = text.trim().toUpperCase();

        return Arrays.stream(WordType.values())
                .filter(type -> type.name().equals(normalized) ||
                        Arrays.asList(type.aliases).contains(normalized))
                .findFirst()
                .orElseGet(() -> {
                    // 针对特殊的逻辑处理：比如“动词”默认归为 VERB_1
                    if (normalized.contains("动词")) return VERB_UNCLASSIFIED;
                    return UNKNOWN; // 默认
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
