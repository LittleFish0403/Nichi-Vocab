package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.WordDto;
import top.sakablog.nichi.repository.WordRepository;
import top.sakablog.nichi.service.WordService;

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
public class WordServiceImpl implements WordService {
    @Autowired
    private WordRepository wordRepository;

    public List<Word> findAllWordsByWordBookId(Long wordBookId){
        return null;
    }

    @Override
    public Word saveWord(Word word) {
        return wordRepository.save(word);
    }

    @Override
    @Transactional
    public List<Word> saveAllWords(List<Word> words) {
        return wordRepository.saveAll(words);
    }

    @Override
    public Boolean saveWordByWordBookId(Long wordBookId, Word word) {
        return null;
    }
}
