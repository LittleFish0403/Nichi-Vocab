package top.sakablog.nichi.common.exception;

import lombok.Getter;
import top.sakablog.nichi.common.ResultCode;

/**
 * SystemException
 * <p>
 * 系统异常
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Getter
public class SystemException extends RuntimeException {
    private final Integer code;

    public SystemException(String message) {
        super(message);
        this.code = ResultCode.SYSTEM_ERROR.getCode();
    }

    // 可以增加一个构造函数，把原始异常 e 传进来，方便记录日志
    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.SYSTEM_ERROR.getCode();
    }
}
