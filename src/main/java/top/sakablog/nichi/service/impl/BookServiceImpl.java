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
import top.sakablog.nichi.model.dto.book.BookDto;
import top.sakablog.nichi.model.dto.book.WordImportDto;
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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Book importBookFromCsv(MultipartFile file) {
        List<WordImportDto> importWords;
        Book savedBook;
        String originalFilename = file.getOriginalFilename();

        try {
            log.info("开始导入上传的文件: {}", originalFilename);
            if (file.isEmpty()) {
                throw new BusinessException("上传的文件不能为空");
            }
            savedBook = createBook(originalFilename, "<NULL>");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("初始化词书失败: {}", e.getMessage());
            throw new SystemException("初始化词书失败");
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            importWords = csvUtils.beanBuilder(reader, WordImportDto.class);
            log.info("CSV解析成功，获取到 {} 条原始数据", importWords.size());
            savedBook.setCount(importWords.size());
            savedBook = bookRepository.save(savedBook);
        } catch (Exception e) {
            log.error("CSV解析失败: {}", e.getMessage());
            throw new BusinessException("CSV解析失败，请核对CSV格式或编码(建议UTF-8): " + e.getMessage());
        }

        try {
            List<Word> words = wordMapper.toEntityListFromImportDto(importWords);
            List<Word> savedWords = wordService.saveAllWords(words);
            bookWordService.saveAllBookWords(savedWords, savedBook);
        } catch (Exception e) {
            log.error("单词保存失败: {}", e.getMessage());
            throw new SystemException("单词保存失败，数据库报错", e);
        }

        log.info("词书导入成功: {}", savedBook.getName());
        return savedBook;
    }

    @Override
    public Book createBook(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "词书名称不能为空");
        }
        if (description == null || description.trim().isEmpty()) {
            description = "<NULL>";
        }

        Book book = new Book();
        book.setName(name.trim());
        book.setDescription(description.trim());
        book.setCount(0);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(BookDto bookDto) {
        if (bookDto == null || bookDto.getId() == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "词书ID不能为空");
        }

        Book savedBook = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到对应的词书，ID: " + bookDto.getId()));

        applyBookUpdates(savedBook, bookDto);
        return bookRepository.save(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        try {
            bookRepository.deleteById(bookId);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "删除词书失败，未找到对应的词书: " + bookId);
        } catch (Exception e) {
            throw new SystemException("删除词书失败，数据库异常", e);
        }
    }

    @Override
    public Book getWordBookById(Long bookId) {
        try {
            return bookRepository.findById(bookId)
                    .orElseThrow(() -> new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "未找到对应的词书，ID: " + bookId));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException("获取词书信息失败，数据库异常", e);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new SystemException("获取所有词书信息失败，数据库异常", e);
        }
    }

    @Override
    public boolean existsByBookId(Long bookId) {
        try {
            return bookRepository.existsById(bookId);
        } catch (Exception e) {
            throw new SystemException("数据库查询出现问题", e);
        }
    }

    @Override
    public List<Book> getSelectedBooksByUserId(Long userId) {
        try {
            return bookRepository.findByUserBookProgressId(userId);
        } catch (Exception e) {
            throw new SystemException("根据用户ID查询用户选择的词书关系出现问题: " + e.getMessage());
        }
    }

    private void applyBookUpdates(Book savedBook, BookDto bookDto) {
        if (bookDto.getName() != null && !bookDto.getName().trim().isEmpty()) {
            savedBook.setName(bookDto.getName().trim());
        }
        if (bookDto.getCount() != null) {
            savedBook.setCount(bookDto.getCount());
        }
        if (bookDto.getDescription() != null && !bookDto.getDescription().trim().isEmpty()) {
            savedBook.setDescription(bookDto.getDescription().trim());
        }
        if (bookDto.getCategory() != null) {
            savedBook.setCategory(bookDto.getCategory());
        }
        if (bookDto.getCoverUrl() != null && !bookDto.getCoverUrl().trim().isEmpty()) {
            savedBook.setCoverUrl(bookDto.getCoverUrl().trim());
        }
        if (bookDto.getVersion() != null && !bookDto.getVersion().trim().isEmpty()) {
            savedBook.setVersion(bookDto.getVersion().trim());
        }
        if (bookDto.getVisibility() != null) {
            savedBook.setVisibility(bookDto.getVisibility());
        }
        if (bookDto.getStatus() != null) {
            savedBook.setStatus(bookDto.getStatus());
        }
        if (bookDto.getDailyGoal() != null) {
            savedBook.setDailyGoal(bookDto.getDailyGoal());
        }
        if (bookDto.getSortOrder() != null) {
            savedBook.setSortOrder(bookDto.getSortOrder());
        }
    }
}
