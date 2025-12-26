package top.sakablog.nichi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.WordBook}
 */
@Value
@Schema(description = "用于更新词书信息的DTO")
public class UpdateWordBookDto implements Serializable {
    Long id;
    String name;
    String level;
    String description;
}