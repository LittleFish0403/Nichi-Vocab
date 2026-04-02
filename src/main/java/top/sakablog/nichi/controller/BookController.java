package top.sakablog.nichi.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.sakablog.nichi.common.ResultCode;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.BookMapper;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.model.entity.word.Word;
import top.sakablog.nichi.model.dto.book.BookDto;
import top.sakablog.nichi.model.dto.word.WordDto;
import top.sakablog.nichi.service.BookService;
import top.sakablog.nichi.service.WordService;

import java.util.List;

/**
 * BookController
 * <p>
 * 单词本控制器
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "Word Book Management", description = "APIs for managing word books")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    WordService wordService;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    WordMapper wordMapper;

    /**
     * 创建新词书
     */
    @PostMapping("/")
    @Operation(summary = "创建新词书", description = "创建一个新的词书，需提供名称和描述")
    @SaCheckPermission("book.create")
    public RestResponse<BookDto> createWordBook(
            @Parameter(description = "词书名字", required = true) String name,
            @Parameter(description = "词书描述", required = true) String description) {
        Book book = bookService.createBook(name, description);
        return RestResponse.success(bookMapper.toWordBookDto(book));
    }

    /**
     * 根据词书ID 删除词书
     */
    @DeleteMapping("/{wordBookId}")
    @Operation(summary = "删除词书", description = "根据词书ID删除对应的词书")
    @SaCheckPermission("book.delete")
    public RestResponse<Void> deleteWordBook(@PathVariable Long wordBookId) {
        Boolean result;
        bookService.deleteBook(wordBookId);
        return RestResponse.success(null);
    }

    /**
     * 根据词书Json 编辑词书信息
     */
    @PutMapping("/")
    @Operation(summary = "编辑词书", description = "根据词书ID编辑对应的词书信息")
    @SaCheckPermission("book.update")
    public RestResponse<BookDto> editWordBook(
           @Parameter(description = "词书实体", required = true) BookDto bookDto) {
        Book book = bookService.updateBook(bookDto);
        return RestResponse.success(bookMapper.toWordBookDto(book));
    }

    /**
     * 根据词书ID 获取词书信息
     */
    @GetMapping("/{wordBookId}")
    @Operation(summary = "获取词书信息", description = "根据词书ID获取对应的词书信息")
    public RestResponse<BookDto> getWordBook(
           @PathVariable Long wordBookId) {
        Book book = bookService.getWordBookById(wordBookId);
        return RestResponse.success(bookMapper.toWordBookDto(book));
    }

    /**
     * 根据词书ID 获取词书中的单词列表
     */
    @GetMapping("/{wordBooksId}/words")
    @Operation(summary = "获取词书中的单词列表", description = "根据词书ID获取对应的单词列表")
    public RestResponse<List<WordDto>> getWordsInWordBook(
           @PathVariable Long wordBooksId) {
        List<Word> words = wordService.findAllWordsByBookId(wordBooksId);
//        return RestResponse.success(wordMapper.toDtoList(words));
        return null;
    }

    /**
     * 获取所有词书信息
     */
    @GetMapping("/all")
    @Operation(summary = "获取所有词书信息", description = "获取所有的词书信息")
    public RestResponse<List<BookDto>> getAllWordBooks() {
        List<Book> books = bookService.getAllBooks();
        return RestResponse.success(bookMapper.toWordBookDtoList(books));
    }

    /**
     * 导入csv词书
     */
    @PostMapping("/import-csv")
    @Operation(summary = "导入CSV词书", description = "从CSV文件导入词书")
    @SaCheckPermission("book.create")
    public RestResponse<BookDto> importCsvWordBook(
            @RequestParam("file") MultipartFile csvFile
    ){
        System.out.println("Importing CSV Word Book...");
        if(csvFile==null || csvFile.isEmpty()){
            return RestResponse.fail(ResultCode.RESOURCE_NOT_FOUND, "Import Failed: CSV file is empty");
        }

        Book book = bookService.importBookFromCsv(csvFile);
        return RestResponse.success(bookMapper.toWordBookDto(book));
    }
}
