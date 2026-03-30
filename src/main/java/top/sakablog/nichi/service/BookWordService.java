package top.sakablog.nichi.service;

import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.model.entity.BookWord;
import top.sakablog.nichi.model.entity.word.Word;

import java.util.List;

/**
 * BookWordService
 * <p>
 * 单词和单词本关联服务
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface BookWordService {

    /**
     * 保存单词和单词本的关联
     *
     * @param word     单词对象
     * @param book 单词本对象
     * @return 保存后的ListWord对象
     */
    public BookWord saveBookWord(Word word, Book book);

    /**
     * 批量保存单词和单词本的关联
     *
     * @param words    单词对象列表
     * @param book 单词本对象
     * @return 保存后的ListWord对象列表
     */
    public List<BookWord> saveAllBookWords(List<Word> words, Book book);
}
