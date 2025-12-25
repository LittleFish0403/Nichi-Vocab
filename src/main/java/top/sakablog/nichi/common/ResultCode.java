package top.sakablog.nichi.common;

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
    BUSINESS_ERROR(400, "业务逻辑错误"),
    SYSTEM_ERROR(500, "系统内部异常"),

    NOT_FOUND(404, "接口不存在"),
    FORBIDDEN(403, "资源拒绝访问"),
    UNAUTHORIZED(401, "未认证（签名错误）"),

    NULL_POINT(200002, "空指针异常"),
    PARAM_ERROR(200001, "参数错误"),
    RESOURCE_NOT_FOUND(200003, "请求资源未找到");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
