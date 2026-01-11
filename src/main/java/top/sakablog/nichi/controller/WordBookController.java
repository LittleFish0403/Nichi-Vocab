package top.sakablog.nichi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.WordBookMapper;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.UpdateWordBookDto;
import top.sakablog.nichi.model.dto.WordBookDto;
import top.sakablog.nichi.service.WordBookService;

import java.util.List;

/**
 * WordBookController
 * <p>
 * 单词本控制器
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/word-books")
@Tag(name = "Word Book Management", description = "APIs for managing word books")
public class WordBookController {
    @Autowired
    WordBookService wordBookService;

    @Autowired
    WordBookMapper wordBookMapper;

    /**
     * 创建新词书
     */
    @PostMapping("/")
    @Operation(summary = "创建新词书", description = "创建一个新的词书，需提供名称和描述")
    public RestResponse<WordBookDto> createWordBook(
            @Parameter(description = "词书名字", required = true) String name,
            @Parameter(description = "词书描述", required = true) String description) {
        WordBook wordBook;
        try {
            wordBook = wordBookService.newWordBook(name, description);
        } catch (Exception e) {
            return RestResponse.fail(null, "Create Word Book Failed: " + e.getMessage());
        }
        return RestResponse.success(wordBookMapper.toWordBookDto(wordBook));
    }

    /**
     * 根据词书ID 删除词书
     */
    @DeleteMapping("/{wordBookId}")
    @Operation(summary = "删除词书", description = "根据词书ID删除对应的词书")
    public RestResponse<Void> deleteWordBook(@PathVariable Long wordBookId) {
        Boolean result;
        try {
             wordBookService.deleteWordBook(wordBookId);
            return RestResponse.success(null);
        } catch (Exception e) {
            return RestResponse.fail(null, "Delete Word Book Failed: " + e.getMessage());
        }
    }

    /**
     * 根据词书Json 编辑词书信息
     */
    @PutMapping("/")
    @Operation(summary = "编辑词书", description = "根据词书ID编辑对应的词书信息")
    public RestResponse<WordBookDto> editWordBook(
           @Parameter(description = "词书实体", required = true) UpdateWordBookDto updateWordBookDto) {
        WordBook wordBook;
        try {
            wordBook = wordBookService.updateWordBook(updateWordBookDto);
        } catch (Exception e) {
            return RestResponse.fail(null, "Edit Word Book Failed: " + e.getMessage());
        }
        return RestResponse.success(wordBookMapper.toWordBookDto(wordBook));
    }

    /**
     * 根据词书ID 获取词书信息
     */
    @GetMapping("/{wordBookId}")
    @Operation(summary = "获取词书信息", description = "根据词书ID获取对应的词书信息")
    public RestResponse<WordBookDto> getWordBook(
           @PathVariable Long wordBookId) {
        WordBook wordBook;
        try {
            wordBook = wordBookService.getWordBookById(wordBookId);
            return RestResponse.success(wordBookMapper.toWordBookDto(wordBook));
        } catch (Exception e) {
            return RestResponse.fail(null, "Get Word Book Failed: " + e.getMessage());
        }
    }

    /**
     * 获取所有词书信息
     */
    @GetMapping("/all")
    @Operation(summary = "获取所有词书信息", description = "获取所有的词书信息")
    public RestResponse<Iterable<WordBookDto>> getAllWordBooks() {
        List<WordBook> wordBooks;
        try {
            wordBooks = wordBookService.getAllWordBooks();
            return RestResponse.success(wordBookMapper.toWordBookDtoList(wordBooks));
        } catch (Exception e) {
            return RestResponse.fail(null, "Get All Word Books Failed: " + e.getMessage());
        }
    }
}
