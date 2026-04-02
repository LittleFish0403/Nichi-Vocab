package top.sakablog.nichi.service;

import top.sakablog.nichi.model.dto.word.WordSimpleDto;
import top.sakablog.nichi.model.entity.word.Word;
import top.sakablog.nichi.model.dto.word.WordDto;

import java.util.List;

/**
 * WordService
 * <p>
 * 
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */public interface WordService {

    /**
     * 根据单词id返回完整WordDto
     */
    WordDto getWordDtoById(Long id);

    /**
     * 根据单词id返回完整Word
     */
     Word getWordById(Long id);

    /**
     * 根据单词id返回基础WordSimple
     */
    WordSimpleDto getWordSimpleDtoById(Long id);
}
