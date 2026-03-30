package top.sakablog.nichi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.model.entity.BookWord;
import top.sakablog.nichi.model.entity.BookWordId;
import top.sakablog.nichi.model.entity.word.Word;
import top.sakablog.nichi.model.enums.WordType;

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
                .setWordType(WordType.NOUN_COMMON)
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
                .setWordType(WordType.NOUN_COMMON)
                .setSource("新标初_01");


        Word w2 = new Word().setJapaneseWord("日本人")
                .setKanaReading("にほんじん")
                .setMeaningCn("日本人")
                .setWordType(WordType.NOUN_COMMON)
                .setSource("新标初_01");

        List<Word> newWords = Arrays.asList(w1, w2);

        // Act: 批量保存
        List<Word> savedWords = wordRepository.saveAll(newWords);

        // 3. 验证数据库中的总记录数（原有一条 + 新增两条 = 三条）
        assertThat(wordRepository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("测试根据单词本ID查询单词列表 (修复版)")
    void whenFindWordsByWordBookId_thenReturnWordList() {
        // 1. 持久化 Word (不要手动 set ID，让数据库生成)
        Word w1 = new Word()
                .setJapaneseWord("アメリカ人")
                .setKanaReading("アメリカじん")
                .setMeaningCn("美国人")
                .setWordType(WordType.NOUN_COMMON)
                .setSource("新标初_01");
        w1 = entityManager.persistFlushFind(w1);

        // 2. 持久化 Book
        Book wb = new Book()
                .setName("测试单词本")
                .setDescription("用于测试的单词本")
                .setCount(1);
        wb = entityManager.persistFlushFind(wb);

        // 3. 建立并持久化中间表关联
        BookWord savedBookWord = new BookWord()
                .setId(new BookWordId().setWordId(w1.getId()).setWordBookId(wb.getId()))
                .setWord(w1)
                .setBook(wb);
        entityManager.persist(savedBookWord);

        // 强制同步到 H2 内存数据库
        entityManager.flush();
        entityManager.clear(); // 清理一级缓存，确保接下来的查询是查数据库而不是查内存

        // 4. 执行查询
        // 建议 Repository 方法名改为 findWordsByBookId (注意大小写规范)
        List<Word> words = wordRepository.findWordsByBookId(wb.getId());

        // 5. 验证
        assertThat(words.get(0).getJapaneseWord()).isEqualTo("アメリカ人");
    }

}
