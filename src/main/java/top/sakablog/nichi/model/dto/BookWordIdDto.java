package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import top.sakablog.nichi.model.BookWordId;

import java.io.Serializable;

/**
 * DTO for {@link BookWordId}
 */
@Value
@Schema(description = "BookWord关联表的ID类，用于复合主键映射")
public class BookWordIdDto implements Serializable {
    Long wordId;
    Long wordBookId;
}