package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.ListWordId}
 */
@Value
@Schema(description = "ListWord关联表的ID类，用于复合主键映射")
public class ListWordIdDto implements Serializable {
    Long wordId;
    Long wordBookId;
}