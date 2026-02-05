package top.sakablog.nichi.common;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.common.response.RestResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 捕获你自定义的业务异常
    @ExceptionHandler(BusinessException.class)
    public RestResponse<Object> handleBusinessException(BusinessException e) {
        // 返回你定义的标准响应格式，比如 code=400
        return RestResponse.fail(e);
    }

    // 捕获你自定义的业务异常
    @ExceptionHandler(SystemException.class)
    public RestResponse<Object> handleBusinessException(SystemException e) {
        // 返回你定义的标准响应格式，比如 code=400
        return RestResponse.fail(e);
    }

    // 捕获Sa-Token的未登录异常
    @ExceptionHandler(NotLoginException.class)
    public RestResponse handlerException(NotLoginException e) {
        return RestResponse.fail(ResultCode.UNAUTHORIZED, "未登录，请先登录");
    }

    // 捕获系统未知的运行异常（比如空指针）
    @ExceptionHandler(Exception.class)
    public RestResponse<Object> handleException(Exception e) {
        return RestResponse.fail(ResultCode.UNKNOWN_ERROR, "服务器出现未知错误，请稍后再试");
    }
}
