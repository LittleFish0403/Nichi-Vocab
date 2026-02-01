package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.WordBookDto;
import top.sakablog.nichi.model.dto.WordImportDto;
import top.sakablog.nichi.repository.WordBookRepository;
import top.sakablog.nichi.service.ListWordService;
import top.sakablog.nichi.service.WordBookService;
import top.sakablog.nichi.service.WordService;
import top.sakablog.nichi.utils.CsvUtils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

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
    public WordBook importWordBookFromCsv(MultipartFile file) { // 1. 参数改为 MultipartFile
        List<WordImportDto> importWords;
        WordBook savedWordBook;

        // 获取原始文件名作为单词本名称
        String originalFilename = file.getOriginalFilename();

        try {
            log.info("开始导入上传的文件: {}", originalFilename);
            if (file.isEmpty()) throw new BusinessException("上传的文件为空");

            // 2. 初始化单词本对象
            savedWordBook = newWordBook(originalFilename, "<NULL>");

        } catch (BusinessException e){
            throw e;
        } catch (Exception e) {
            log.error("初始化单词本失败: {}", e.getMessage());
            throw new SystemException("初始化单词本失败");
        }

        // 3. 调用 CSV 工具解析文件流
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            // 注意：这里需要你的 csvUtils.beanBuilder 支持 Reader 类型参数
            importWords = csvUtils.beanBuilder(reader, WordImportDto.class);

            log.info("CSV解析成功，获取到 {} 条原始数据", importWords.size());
            savedWordBook.setCount(importWords.size());
        } catch (Exception e){
            log.error("CSV解析失败: {}", e.getMessage());
            throw new BusinessException("CSV解析失败，请核对CSV格式或编码(建议UTF-8): " + e.getMessage());
        }

        // 4. 映射为Word实体并保存到数据库
        try {
            List<Word> words = wordMapper.toEntityListFromImportDto(importWords);
            List<Word> savedWords = wordService.saveAllWords(words);
            listWordService.saveAllListWord(savedWords, savedWordBook);
        } catch (Exception e) {
            log.error("单词保存失败: {}", e.getMessage());
            throw new SystemException("单词保存失败，数据库报错: ", e);
        }

        // 5. 返回保存的单词本实体
        log.info("单词本导入成功: {}", savedWordBook.getName());
        return savedWordBook;
    }

    @Override
    public WordBook newWordBook(String name, String description) {
        if(name==null || name.trim().isEmpty()){
            throw new BusinessException(ResultCode.PARAM_ERROR, "词本名称不能为空");
        }
        if(description==null || description.trim().isEmpty()){
            description = "<NULL>";
        }
        WordBook wordbook = new WordBook();
        wordbook.setName(name);
        wordbook.setDescription(description);
        wordbook.setCount(0);
        return wordBookRepository.save(wordbook);
    }

    @Override
    public void deleteWordBook(Long wordBookId) {
        if (!wordBookRepository.existsById(wordBookId)) {
            // 这里抛出业务异常，前端会收到“资源不存在”的提示
            throw new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "删除失败：词书 ID [" + wordBookId + "] 不存在");
        }
        try{
            wordBookRepository.deleteById(wordBookId);
        } catch (Exception e){
            throw new SystemException( "删除词书失败，数据库异常", e);
        }
    }

    @Override
    public WordBook updateWordBook(WordBookDto wordBook){
        WordBook savedWordBook = wordBookRepository.findById(wordBook.getId())
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_ERROR, "未找到对应的词书，ID：" + wordBook.getId()));
        if (!Objects.equals(wordBook.getName(), "")) {
            savedWordBook.setName(wordBook.getName());
        }
        if (!Objects.equals(wordBook.getLevel(), "")) {
            savedWordBook.setLevel(wordBook.getLevel());
        }
        if (!Objects.equals(wordBook.getDescription(), "")) {
            savedWordBook.setDescription(wordBook.getDescription());
        }
        return wordBookRepository.save(savedWordBook);
    }

    @Override
    public void editWordBookName(Long wordBookId, String newName) {
        WordBook wordbook = wordBookRepository.findById(wordBookId)
                .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
        try {
            wordbook.setName(newName);
            wordBookRepository.save(wordbook);
        } catch (Exception e){
            throw new SystemException("修改单词本名称失败，数据库异常", e);
        }
    }

    @Override
    public void editWordBookLevel(Long wordBookId, String Level) {
        WordBook wordbook = wordBookRepository.findById(wordBookId)
                .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
        try {
            wordbook.setLevel(Level);
            wordBookRepository.save(wordbook);
        } catch (Exception e){
            throw new SystemException("修改单词本等级失败，数据库异常", e);
        }
    }

    @Override
    public void editWordBookDescription(Long wordBookId, String newDescription) {
        WordBook wordbook = wordBookRepository.findById(wordBookId)
                .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
        try { wordbook.setDescription(newDescription);
            wordBookRepository.save(wordbook);
        } catch (Exception e){
            throw new SystemException("修改单词本描述失败，数据库异常", e);
        }
    }

    @Override
    public WordBook getWordBookById(Long wordBookId) {
       try{
              return wordBookRepository.findById(wordBookId)
                     .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
         } catch (BusinessException e){
              throw e;
         } catch (Exception e){
              throw new SystemException("获取单词本信息失败，数据库异常", e);
       }
    }

    @Override
    public List<WordBook> getAllWordBooks() {
        try{
            return wordBookRepository.findAll();
        } catch (Exception e) {
            throw new SystemException("获取所有单词本信息失败，数据库异常", e);
        }
    }
}

