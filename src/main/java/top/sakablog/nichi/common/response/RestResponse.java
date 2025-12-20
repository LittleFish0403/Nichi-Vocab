package top.sakablog.nichi.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.sakablog.nichi.common.ResultCode;

/**
 * RestResponse
 * <p>
 * 统一API响应格式
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Data
@Schema(description = "统一API响应格式")
public class RestResponse<T> {
    private Boolean success; // 是否成功 (true/false)
    private Integer code;    // 状态码 (如 200, 405)
    private String message;  // 提示信息
    private T data;          // 数据体

    // 私有构造，统一由静态方法创建
    private RestResponse(Boolean success, ResultCode resultCode, T data) {
        this.success = success;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    /**
     * 成功：数据 + 消息
     * @param data 返回数据
     * @return RestResponse<T>
     */
    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(true, ResultCode.SUCCESS, data);
    }

    /** 成功：无数据，仅表示操作成功 */
    public static <T> RestResponse<Void> success() {
        return new RestResponse<>(true, ResultCode.SUCCESS, null);
    }

    /** 成功：无数据，自定义成功消息 */
    public static <T> RestResponse<Void> success(String message) {
        RestResponse<Void> response = new RestResponse<>(true, ResultCode.SUCCESS, null);
        response.setMessage(message);
        return response;
    }

    /** 失败：枚举 */
    public static <T> RestResponse<T> fail(ResultCode resultCode) {
        return new RestResponse<>(false, resultCode, null);
    }

    /** 失败：枚举 + 自定义消息 */
    public static <T> RestResponse<T> fail(ResultCode resultCode, String customMessage) {
        RestResponse<T> response = new RestResponse<>(false, resultCode, null);
        response.setMessage(customMessage);
        return response;
    }
}
