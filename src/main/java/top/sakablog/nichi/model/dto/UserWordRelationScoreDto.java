package top.sakablog.nichi.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Getter
@Setter
public class UserWordRelationScoreDto {
    private Long userWordRelationId;
    private Integer score;
    private Integer learning_status;
    private Integer review_status;
}
