package top.sakablog.nichi.exception;

import lombok.Getter;

/**
 * ResultCode
 * <p>
 * 结果代码枚举类
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    BUSINESS_ERROR(400, "业务逻辑错误"), // 用户填错了、单词重了
    SYSTEM_ERROR(500, "系统内部异常");    // 数据库挂了、代码Bug

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
