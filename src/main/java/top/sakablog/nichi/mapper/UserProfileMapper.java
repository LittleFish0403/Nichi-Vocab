package top.sakablog.nichi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import top.sakablog.nichi.model.entity.user.UserProfile;
import top.sakablog.nichi.model.dto.user.UserProfileDto;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    @Mapping(target = "selectedBookId", source = "selectedBook.id")
    @Mapping(target = "selectedUserBookProgressId", source = "selectedUserBookProgress.id")
    UserProfileDto toDto(UserProfile userProfile);
    UserProfile toEntity(UserProfileDto userProfileDto);
}
