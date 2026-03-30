package top.sakablog.nichi.model.dto.study;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sakablog.nichi.model.entity.study.UserBookProgress;

import java.io.Serializable;

/**
 * DTO for {@link UserBookProgress}
 */
@Data // 生成 Getter, Setter, toString, equals, hashCode
@AllArgsConstructor // 生成全参构造
@NoArgsConstructor
@Schema(description = " 用户词书进度请求DTO，包含用户ID和词书ID，用于查询或更新用户在特定词书中的学习进度 ")
public class UserBookProgressRequestDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long bookId;
}