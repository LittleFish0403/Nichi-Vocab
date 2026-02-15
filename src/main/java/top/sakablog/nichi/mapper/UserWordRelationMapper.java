package top.sakablog.nichi.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import top.sakablog.nichi.model.UserWordRelation;
import top.sakablog.nichi.model.dto.UserWordRelationDto;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface UserWordRelationMapper {
    UserWordRelation toEntity(UserWordRelationDto userWordRelationDto);
    UserWordRelationDto toDto(UserWordRelation userWordRelation);
    List<UserWordRelationDto> toDtoList(List<UserWordRelation> userWordRelationList);
    List<UserWordRelation> toEntityList(List<UserWordRelationDto> userWordRelationDtoList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserWordRelation partialUpdate(UserWordRelationDto userWordRelationDto, @MappingTarget UserWordRelation userWordRelation);
}
