package top.sakablog.nichi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sakablog.nichi.common.response.RestResponse;
import top.sakablog.nichi.mapper.UserBookProgressMapper;
import top.sakablog.nichi.model.UserBookProgress;
import top.sakablog.nichi.model.dto.*;
import top.sakablog.nichi.service.UserService;
import top.sakablog.nichi.service.UserBookProgressService;
import top.sakablog.nichi.service.LearningService;
import top.sakablog.nichi.service.WordMasteryService;

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
@RequestMapping("/api/v1/learning")
public class LearningController {
    @Autowired
    private UserBookProgressService userBookProgressService;

    @Autowired
    private WordMasteryService userWordRelationService;

    @Autowired
    private UserBookProgressMapper userBookProgressMapper;

    @Autowired
    private LearningService learningService;

    @Autowired
    private UserService userService;

    /**
     * 用户选择单词本
     * @param userBookProgressRequestDto 包含用户ID和单词本ID的请求DTO
     * @return RestResponse<Void>
     */
    @PostMapping("/book")
    @SaCheckLogin
    public RestResponse<UserBookProgressDto> selectWordBook(
            @RequestBody UserBookProgressRequestDto userBookProgressRequestDto){
        log.info("收到选书请求: {}", userBookProgressRequestDto);
        Long userId = userBookProgressRequestDto.getUserId();
        Long wordBookId = userBookProgressRequestDto.getBookId();
        log.info("解析到的 ID -> id: {}, bookId: {}", userId, wordBookId);
        return RestResponse.success(userBookProgressMapper.toDto(userBookProgressService.createUserBookProgress(userId, wordBookId)));
    }

    /**
     * 根据用户id 返回 user_word_book_id
     * @return RestResponse<Long> 包含 user_word_book_id 的响应
     */
    @GetMapping("/active-progress-id")
    @SaCheckLogin
    public RestResponse<Long> getUserWordBookId() {
        Long userId = StpUtil.getLoginIdAsLong();
        Long wordBookId = userService.findUserByUserId(userId).getUserProfile().getSelectedBookId();
        UserBookProgress userBookProgress = userBookProgressService.getUserBookProgressByUserIdAndBookId(userId, wordBookId);
        return RestResponse.success(userBookProgress.getId());
    }


    @GetMapping("/words/order/session")
    @SaCheckLogin
    public RestResponse<List<WordMasteryDto>> fetchWordsByOrderForSession(){
        Long userId = StpUtil.getLoginIdAsLong();
        Long ubpId = userService.findUserByUserId(userId).getUserProfile().getSelectedUserBookProgressId();
        return RestResponse.success(learningService.fetchWordsByOrderForSession(ubpId, userId));
    }

    @GetMapping("/words/order/sync")
    @SaCheckLogin
    public RestResponse<List<WordMasteryDto>> fetchWordsByOrderForSync(){
        Long userId = StpUtil.getLoginIdAsLong();
        Long ubpId = userService.findUserByUserId(userId).getUserProfile().getSelectedUserBookProgressId();
        return RestResponse.success(learningService.fetchWordsByOrderForSync(ubpId, userId));
    }

    @PostMapping("/words/score")
    @SaCheckLogin
    public RestResponse<List<WordMasteryDto>> updateWordsByScore(@RequestBody List<WordMasteryScoreDto> scoreDtos){
        Long userId = StpUtil.getLoginIdAsLong();
        Long ubpId = userService.findUserByUserId(userId).getUserProfile().getSelectedUserBookProgressId();
        return  RestResponse.success(learningService.updateWordMastery(scoreDtos, ubpId));
    }

}
