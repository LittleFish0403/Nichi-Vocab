package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.dto.word.WordSimpleDto;
import top.sakablog.nichi.model.entity.word.Word;
import top.sakablog.nichi.model.dto.word.WordDto;
import top.sakablog.nichi.repository.word.WordRepository;
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
@Slf4j
public class WordServiceImpl implements WordService {
    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordMapper wordMapper;

    @Override
    @Transactional()
    public WordDto getWordDtoById(Long id){
        try {
            return wordMapper.toWordDto(getWordById(id));
        } catch (Exception e){
            throw new SystemException(e.getMessage());
        }
    }

    @Override
    public Word getWordById(Long id){
        try {
            return wordRepository.findById(id).orElseThrow();
        } catch (Exception e){
            throw new SystemException(e.getMessage());
        }
    }

    @Override
    public WordSimpleDto getWordSimpleDtoById(Long id){
        try {
            return wordMapper.toSimpleWordDto(getWordById(id));
        } catch (Exception e){
            throw new SystemException(e.getMessage());
        }
    }
}
