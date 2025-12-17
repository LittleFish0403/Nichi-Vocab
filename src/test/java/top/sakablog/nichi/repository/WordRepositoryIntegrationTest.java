package top.sakablog.nichi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import top.sakablog.nichi.model.Word;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Integration test for the {@link WordRepository} class.
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */

@DataJpaTest
public class WordRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private  WordRepository wordRepository;

    private Word savedWord;


    /**
     * 初始化测试数据
     */
    @BeforeEach
    void setup() {
        // 1. 清理环境
        wordRepository.deleteAll();

        // 2. 插入一个用于测试的基础对象
        Word newWord = new Word()
                .setJapaneseWord("中国人")
                .setKanaReading("ちゅうごくじん")
                .setMeaningCn("中国人")
                .setWordType(Word.WordType.NOUN_COMMON)
                .setSource("新标初_01");

        // 使用 entityManager.persistAndFlush() 确保数据立即写入内存数据库
        savedWord = entityManager.persistAndFlush(newWord);
    }

    @Test
    void whenFindWordById_thenReturnWord() {
        Word word = wordRepository.findById(savedWord.getId()).orElse(null);

        assertThat(word).isEqualTo(savedWord);
    }

    @Test
    void whenSaveAllWords_ShouldPersistAllWords() {
        Word w1 = new Word()
                .setJapaneseWord("アメリカ人")
                .setKanaReading("アメリカじん")
                .setMeaningCn("美国人")
                .setWordType(Word.WordType.NOUN_COMMON)
                .setSource("新标初_01");


        Word w2 = new Word().setJapaneseWord("日本人")
                .setKanaReading("にほんじん")
                .setMeaningCn("日本人")
                .setWordType(Word.WordType.NOUN_COMMON)
                .setSource("新标初_01");

        List<Word> newWords = Arrays.asList(w1, w2);

        // Act: 批量保存
        List<Word> savedWords = wordRepository.saveAll(newWords);

        // 3. 验证数据库中的总记录数（原有一条 + 新增两条 = 三条）
        assertThat(wordRepository.count()).isEqualTo(3);
    }

}
