package top.sakablog.nichi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.model.ListWord;
import top.sakablog.nichi.model.ListWordId;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.repository.ListWordRepository;
import top.sakablog.nichi.service.ListWordService;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Service
public class ListWordServiceImpl implements ListWordService {
    @Autowired
    private ListWordRepository listWordRepository;

    public ListWord saveListWord(Word word, WordBook wordBook){
        ListWord listWord = new ListWord()
                .setId(new ListWordId()
                        .setWordId(word.getId()).
                        setWordBookId(wordBook.getId()))
                .setWordBook(wordBook)
                .setWord(word);
        return listWordRepository.save(listWord);
    }

    @Override
    public List<ListWord> saveAllListWord(List<Word> words, WordBook wordBook){
        List<ListWord> listWords = words.stream()
                .map(word -> new ListWord()
                        .setId(new ListWordId()
                                .setWordId(word.getId())
                                .setWordBookId(wordBook.getId()))
                        .setWord(word)
                        .setWordBook(wordBook))
                .toList();
        return listWordRepository.saveAll(listWords);
    }
}
