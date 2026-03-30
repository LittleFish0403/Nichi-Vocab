package top.sakablog.nichi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.model.entity.BookWord;
import top.sakablog.nichi.model.entity.BookWordId;
import top.sakablog.nichi.model.entity.word.Word;
import top.sakablog.nichi.repository.BookWordRepository;
import top.sakablog.nichi.service.BookWordService;

import java.util.List;

/**
 * BookWordServiceImpl
 * <p>
 * BookWordService的实现类，提供单词和单词本关联的具体业务逻辑实现
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Service
public class BookWordServiceImpl implements BookWordService {

    /**
     * 实例注入
     */
    @Autowired
    private BookWordRepository bookWordRepository;

    /**
      * 保存单词和单词本的关联
     */
    @Override
    public BookWord saveBookWord(Word word, Book book){
        BookWord bookWord = new BookWord()
                .setId(new BookWordId()
                        .setWordId(word.getId()).
                        setBookId(book.getId()))
                .setBook(book)
                .setWord(word);
        return bookWordRepository.save(bookWord);
    }

    /**
     * 批量保存单词和单词本的关联
     */
    @Override
    public List<BookWord> saveAllBookWords(List<Word> words, Book book){
        List<BookWord> bookWords = words.stream()
                .map(word -> new BookWord()
                        .setId(new BookWordId()
                                .setWordId(word.getId())
                                .setBookId(book.getId()))
                        .setWord(word)
                        .setBook(book))
                .toList();
        return bookWordRepository.saveAll(bookWords);
    }
}
