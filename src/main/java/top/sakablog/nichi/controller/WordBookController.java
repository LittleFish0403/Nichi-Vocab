package top.sakablog.nichi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.WordBookMapper;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.WordBookDto;
import top.sakablog.nichi.service.WordBookService;

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
@RequestMapping("/api/v1/wordbook")
@Tag(name = "Word Book Management", description = "APIs for managing word books")
public class WordBookController {
    @Autowired
    WordBookService wordBookService;

    @Autowired
    WordBookMapper wordBookMapper;

    @PostMapping("/new")
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
}
