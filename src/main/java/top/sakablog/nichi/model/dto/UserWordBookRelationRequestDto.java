package top.sakablog.nichi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.UserWordBookRelation}
 */
@Data // 生成 Getter, Setter, toString, equals, hashCode
@AllArgsConstructor // 生成全参构造
@NoArgsConstructor
public class UserWordBookRelationRequestDto implements Serializable {
    Long userId;
    Long wordBookId;
}