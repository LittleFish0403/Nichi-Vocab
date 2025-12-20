package top.sakablog.nichi.common.exception;

import lombok.Getter;
import top.sakablog.nichi.common.ResultCode;

/**
 * BusinessException
 * <p>
 * 业务异常：用于处理用户操作引起的逻辑错误
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR.getCode();
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(ResultCode code, String message) {
        super(message);
        this.code = code.getCode();
    }

}
