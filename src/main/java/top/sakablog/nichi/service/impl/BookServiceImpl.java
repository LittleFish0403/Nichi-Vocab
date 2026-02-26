package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.Book;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.BookDto;
import top.sakablog.nichi.model.dto.WordImportDto;
import top.sakablog.nichi.repository.BookRepository;
import top.sakablog.nichi.service.BookService;
import top.sakablog.nichi.service.BookWordService;
import top.sakablog.nichi.service.WordService;
import top.sakablog.nichi.utils.CsvUtils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * BookServiceImpl
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
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordService wordService;

    @Autowired
    private BookWordService bookWordService;

    @Autowired
    private CsvUtils csvUtils;

    /**
     * 将单次循环插入逻辑改为了一次性插入
     * 提高了运作效率
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Book importBookFromCsv(MultipartFile file) { // 1. 参数改为 MultipartFile
        List<WordImportDto> importWords;
        Book savedBook;

        // 获取原始文件名作为单词本名称
        String originalFilename = file.getOriginalFilename();

        try {
            log.info("开始导入上传的文件: {}", originalFilename);
            if (file.isEmpty()) throw new BusinessException("上传的文件为空");

            // 2. 初始化单词本对象
            savedBook = createBook(originalFilename, "<NULL>");

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
            savedBook.setCount(importWords.size());
        } catch (Exception e){
            log.error("CSV解析失败: {}", e.getMessage());
            throw new BusinessException("CSV解析失败，请核对CSV格式或编码(建议UTF-8): " + e.getMessage());
        }

        // 4. 映射为Word实体并保存到数据库
        try {
            List<Word> words = wordMapper.toEntityListFromImportDto(importWords);
            List<Word> savedWords = wordService.saveAllWords(words);
            bookWordService.saveAllBookWords(savedWords, savedBook);
        } catch (Exception e) {
            log.error("单词保存失败: {}", e.getMessage());
            throw new SystemException("单词保存失败，数据库报错: ", e);
        }

        // 5. 返回保存的单词本实体
        log.info("单词本导入成功: {}", savedBook.getName());
        return savedBook;
    }

    /**
     * 创建单词本
     */
    @Override
    public Book createBook(String name, String description) {
        if(name==null || name.trim().isEmpty()){
            throw new BusinessException(ResultCode.PARAM_ERROR, "词本名称不能为空");
        }
        if(description==null || description.trim().isEmpty()){
            description = "<NULL>";
        }
        Book wordbook = new Book();
        wordbook.setName(name);
        wordbook.setDescription(description);
        wordbook.setCount(0);
        return bookRepository.save(wordbook);
    }

    @Override
    public void deleteBook(Long wordBookId) {
        try{
            bookRepository.deleteById(wordBookId);
        } catch (EmptyResultDataAccessException e){
            throw new BusinessException(ResultCode.PARAM_ERROR, "删除词书失败，未找到对应的词书：" + e.getMessage());
        } catch (Exception e){
            throw new SystemException( "删除词书失败，数据库异常", e);
        }
    }

    @Override
    public Book updateBook(BookDto wordBook){
        Book savedBook = bookRepository.findById(wordBook.getId())
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_ERROR, "未找到对应的词书，ID：" + wordBook.getId()));
        if (!Objects.equals(wordBook.getName(), "")) {
            savedBook.setName(wordBook.getName());
        }
        if (!Objects.equals(wordBook.getLevel(), "")) {
            savedBook.setLevel(wordBook.getLevel());
        }
        if (!Objects.equals(wordBook.getDescription(), "")) {
            savedBook.setDescription(wordBook.getDescription());
        }
        return bookRepository.save(savedBook);
    }

    @Override
    public void updateBookName(Long wordBookId, String newName) {
        Book wordbook = bookRepository.findById(wordBookId)
                .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
        try {
            wordbook.setName(newName);
            bookRepository.save(wordbook);
        } catch (Exception e){
            throw new SystemException("修改单词本名称失败，数据库异常", e);
        }
    }

    @Override
    public void updateBookLevel(Long wordBookId, String Level) {
        Book wordbook = bookRepository.findById(wordBookId)
                .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
        try {
            wordbook.setLevel(Level);
            bookRepository.save(wordbook);
        } catch (Exception e){
            throw new SystemException("修改单词本等级失败，数据库异常", e);
        }
    }

    @Override
    public void updateBookDescription(Long wordBookId, String newDescription) {
        Book wordbook = bookRepository.findById(wordBookId)
                .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
        try { wordbook.setDescription(newDescription);
            bookRepository.save(wordbook);
        } catch (Exception e){
            throw new SystemException("修改单词本描述失败，数据库异常", e);
        }
    }

    @Override
    public Book getWordBookById(Long wordBookId) {
       try{
          return bookRepository.findById(wordBookId)
                 .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到该单词本"));
         } catch (BusinessException e){
              throw e;
         } catch (Exception e){
              throw new SystemException("获取单词本信息失败，数据库异常", e);
       }
    }

    @Override
    public List<Book> getAllBooks() {
        try{
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new SystemException("获取所有单词本信息失败，数据库异常", e);
        }
    }

    @Override
    public boolean existsByBookId(Long bookId){
        try {
            return bookRepository.existsById(bookId);
        } catch (Exception e) {
            throw new SystemException("数据库查询出现问题", e);
        }
    }

    /**
     * 根据用户ID查询用户选择的单词本关系
     */
    public List<Book> getSelectedBooksByUserId(Long userId){
        try {
            return bookRepository.findByUserBookProgressId(userId);
        } catch (Exception e) {
            throw new SystemException("根据用户ID查询用户选择的单词本关系出现问题: " + e.getMessage());
        }
    }
}

