package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.exception.BusinessException;
import top.sakablog.nichi.exception.SystemException;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.ListWord;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.ImportWordDto;
import top.sakablog.nichi.repository.WordBookRepository;
import top.sakablog.nichi.service.ListWordService;
import top.sakablog.nichi.service.WordBookService;
import top.sakablog.nichi.service.WordService;
import top.sakablog.nichi.utils.CsvUtils;


import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * WordBookServiceImpl
 * <p>
 * 实现单词本服务接口
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Service
@Slf4j
public class WordBookServiceImpl implements WordBookService {
    @Autowired
    private WordBookRepository wordBookRepository;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordService wordService;

    @Autowired
    private ListWordService listWordService;

    @Autowired
    private CsvUtils csvUtils;

    /**
     * 将单次循环插入逻辑改为了一次性插入
     * 提高了运作效率
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean importWordBookFromCsv(String fileName){
        Path paths;
        List<ImportWordDto> importWords;
        WordBook savedWordBook;

        try {
            log.info("开始导入文件: {}", fileName);
            savedWordBook = newWordBook(fileName, "<NULL>");
            // 1. 获取CSV文件路径
            URL resource = ClassLoader.getSystemResource(fileName);
            if (resource == null) throw new BusinessException("文件不存在: " + fileName);
            paths = Paths.get(resource.toURI());
        } catch (BusinessException e){
            throw e;
        } catch (Exception e) {
            log.error("导入单词本失败: {}", e.getMessage());
            throw new SystemException("文件路径错误，解析失败：" + fileName);
        }

        // 2. 调用通用的 CSV 解析工具
        try {
            importWords = csvUtils.beanBuilder(paths, ImportWordDto.class);
            log.info("CSV解析成功，获取到 {} 条原始数据", importWords.size());
        } catch (Exception e){
            log.error("CSV解析失败: {}", e.getMessage());
            throw new BusinessException("CSV解析失败，请核对CSV格式: " + e.getMessage());
        }

        // 3. 映射为Word实体并保存到数据库
        try{
            List<Word> words = wordMapper.toEntityListFromImportDto(importWords);
            List<Word> savedWords = wordService.saveAllWords(words);
            listWordService.saveAllListWord(savedWords, savedWordBook);

            return true;
        } catch (Exception e) {
            log.error("单词保存失败: {}", e.getMessage());
            throw new SystemException("单词保存失败，数据库报错: " , e);
        }
    }


    @Override
    public WordBook newWordBook(String name, String description) {
        WordBook wordbook = new WordBook();
        wordbook.setName(name);
        wordbook.setDescription(description);
        wordbook.setCount(0);
        return wordBookRepository.save(wordbook);
    }

    @Override
    public Boolean deleteWordBook(Long wordBookId) {
        wordBookRepository.deleteById(wordBookId);
        return true;
    }

    @Override
    public Boolean editWordBookName(Long wordBookId, String newName) {
        WordBook wordbook = wordBookRepository.findById(wordBookId).orElse(null);
        if (wordbook != null) {
            wordbook.setName(newName);
            wordBookRepository.save(wordbook);
            return true;
        }
        return false;
    }

    @Override
    public Boolean editWordBookDescription(Long wordBookId, String newDescription) {
        WordBook wordbook = wordBookRepository.findById(wordBookId).orElse(null);
        if (wordbook != null) {
            wordbook.setDescription(newDescription);
            wordBookRepository.save(wordbook);
            return true;
        }
        return false;
    }
}

