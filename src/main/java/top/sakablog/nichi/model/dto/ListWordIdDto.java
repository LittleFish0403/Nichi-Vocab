package top.sakablog.nichi.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link top.sakablog.nichi.model.ListWordId}
 */
@Value
public class ListWordIdDto implements Serializable {
    Long wordId;
    Long wordBookId;
}