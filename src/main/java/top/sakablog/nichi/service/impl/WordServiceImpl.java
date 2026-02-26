package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.WordDto;
import top.sakablog.nichi.repository.WordRepository;
import top.sakablog.nichi.service.WordService;

import java.util.List;

/**
 * WordServiceImpl
 * <p>
 * 实现单词服务接口，提供单词相关的具体业务逻辑实现
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

    @Autowired
    private WordMapper wordMapper;

    @Override
    public List<Word> findAllWordsByBookId(Long bookId){
        try {
            return wordRepository.findWordsByBookId(bookId);
        } catch (EmptyResultDataAccessException e){
            throw new SystemException("未找到对应单词本的单词列表，单词本ID: " + bookId);
        } catch (Exception e) {
            throw new SystemException("未找到对应单词本的单词列表");
        }
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
    public Word saveWordByBookId(Long bookId, Word word) {
        return null;
    }

    @Override
    public WordDto getWordById(Long wordId){
        return wordMapper.toDto(wordRepository.findById(wordId)
                .orElseThrow(() -> new SystemException("未找到对应ID的单词，ID: " + wordId)));
    }
}
