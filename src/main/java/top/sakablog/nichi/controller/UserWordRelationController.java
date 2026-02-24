package top.sakablog.nichi.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.UserWordBookRelationMapper;
import top.sakablog.nichi.model.UserWordBookRelation;
import top.sakablog.nichi.model.dto.*;
import top.sakablog.nichi.service.UserService;
import top.sakablog.nichi.service.UserWordBookRelationService;
import top.sakablog.nichi.service.UserWordLearningService;
import top.sakablog.nichi.service.UserWordRelationService;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user-word-relation")
public class UserWordRelationController {
    @Autowired
    private UserWordBookRelationService userWordBookRelationService;

    @Autowired
    private UserWordRelationService userWordRelationService;

    @Autowired
    private UserWordBookRelationMapper userWordBookRelationMapper;

    @Autowired
    private UserWordLearningService userWordLearningService;
    @Autowired
    private UserService userService;

    /**
     * 用户选择单词本
     * @param userWordBookRelationRequestDto 包含用户ID和单词本ID的请求DTO
     * @return RestResponse<Void>
     */
    @PostMapping("/")
    public RestResponse<UserWordBookRelationDto> selectWordBook(
            @RequestBody UserWordBookRelationRequestDto userWordBookRelationRequestDto){
        log.info("收到选书请求: {}", userWordBookRelationRequestDto);
        Long userId = userWordBookRelationRequestDto.getUserId();
        Long wordBookId = userWordBookRelationRequestDto.getWordBookId();
        log.info("解析到的 ID -> userId: {}, wordBookId: {}", userId, wordBookId);
        return RestResponse.success(userWordBookRelationMapper.toDto(userWordBookRelationService.selectWordBook(userId, wordBookId)));
    }

    /**
     * 根据用户id 返回 user_word_book_id
     * @return RestResponse<Long> 包含 user_word_book_id 的响应
     */
    @GetMapping("/user-word-book-id")
    public RestResponse<Long> getUserWordBookId() {
        Long userId = StpUtil.getLoginIdAsLong();
        Long wordBookId = userService.findUserByUserId(userId).getUserProfile().getSelectedWordBookId();
        UserWordBookRelation userWordBookRelation = userWordBookRelationService.getUserWordBookRelationByUserIdAndWordBookId(userId, wordBookId);
        return RestResponse.success(userWordBookRelation.getId());
    }


    @GetMapping("/{userWordBookId}/learning/words/order")
    public RestResponse<List<UserWordRelationDto>> getLearningWordsByOrder(@PathVariable Long userWordBookId){
        return RestResponse.success(userWordLearningService.getLearningWordsByOrder(userWordBookId));
    }

    @GetMapping("/{userWordBookId}/learning/words/order/additional")
    public RestResponse<List<UserWordRelationDto>> getLearningWordsByOrderAdditional(@PathVariable Long userWordBookId){
        return RestResponse.success(userWordLearningService.getLearningWordsByOrderAdditional(userWordBookId));
    }

    @PostMapping("/{userWordBookId}/learning/words/score")
    public RestResponse<List<UserWordRelationDto>> updateLearningWordsInfoByScore(@PathVariable Long userWordBookId,
                                                                           @RequestBody List<UserWordRelationScoreDto> scoreDtos){
        return  RestResponse.success(userWordLearningService.updateUserLearningWords(scoreDtos, userWordBookId));
    }

}
