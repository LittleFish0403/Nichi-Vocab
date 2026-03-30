package top.sakablog.nichi.mapper;

import org.mapstruct.*;
import top.sakablog.nichi.model.entity.user.User;
import top.sakablog.nichi.model.dto.user.UserBaseDto;
import top.sakablog.nichi.model.dto.user.UserDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserBaseDto userBaseDto);

    UserBaseDto toBaseDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserBaseDto userBaseDto, @MappingTarget User user);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdateFromDto(UserDto userDto, @MappingTarget User user);

}