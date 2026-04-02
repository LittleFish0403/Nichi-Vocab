package top.sakablog.nichi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.model.dto.word.WordDto;
import top.sakablog.nichi.model.dto.word.WordSimpleDto;
import top.sakablog.nichi.service.WordService;

/**
 * WordController
 * <p>
 * 单词控制器，提供单词相关的API接口
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/word")
public class WordController {
    @Autowired
    private WordService wordService;

    @GetMapping("/{wordId}")
    public RestResponse<WordDto> getWord(@PathVariable("wordId") Long wordId) {
        return RestResponse.success(wordService.getWordDtoById(wordId));
    }

    @GetMapping("/simple/{wordId}")
    public RestResponse<WordSimpleDto> getWordSimple(@PathVariable("wordId") Long wordId) {
        return RestResponse.success(wordService.getWordSimpleDtoById(wordId));
    }
}
