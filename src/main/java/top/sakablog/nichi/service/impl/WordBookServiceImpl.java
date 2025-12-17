package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Transactional
    public Boolean importWordBookFromCsv(String fileName) throws Exception {
        log.info("开始导入文件: {}", fileName);
        WordBook savedWordBook = newWordBook(fileName, "<NULL>");
        // 1. 获取CSV文件路径
        Path paths = Paths.get(
                ClassLoader.getSystemResource(fileName).toURI()
        );

        // 2. 调用通用的 CSV 解析工具
        List<ImportWordDto> importWords = csvUtils.beanBuilder(paths, ImportWordDto.class);
        log.info("CSV解析成功，获取到 {} 条原始数据", importWords.size());

        // 3. 映射为Word实体并保存到数据库
        List<Word> words = wordMapper.toEntityListFromImportDto(importWords);
        List<Word> savedWords = wordService.saveAllWords(words);
        listWordService.saveAllListWord(savedWords, savedWordBook);

        return true;
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
    public Boolean deleteWordBook(Integer wordBookId) {
        wordBookRepository.deleteById(wordBookId);
        return true;
    }

    @Override
    public Boolean editWordBookName(Integer wordBookId, String newName) {
        WordBook wordbook = wordBookRepository.findById(wordBookId).orElse(null);
        if (wordbook != null) {
            wordbook.setName(newName);
            wordBookRepository.save(wordbook);
            return true;
        }
        return false;
    }

    @Override
    public Boolean editWordBookDescription(Integer wordBookId, String newDescription) {
        WordBook wordbook = wordBookRepository.findById(wordBookId).orElse(null);
        if (wordbook != null) {
            wordbook.setDescription(newDescription);
            wordBookRepository.save(wordbook);
            return true;
        }
        return false;
    }
}

