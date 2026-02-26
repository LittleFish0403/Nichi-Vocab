package top.sakablog.nichi.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import top.sakablog.nichi.model.WordMastery;
import top.sakablog.nichi.model.dto.WordMasteryDto;

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
public interface WordMasteryMapper {
    WordMastery toEntity(WordMasteryDto wordMasteryDto);
    WordMasteryDto toDto(WordMastery wordMastery);
    List<WordMasteryDto> toDtoList(List<WordMastery> wordMasteryList);
    List<WordMastery> toEntityList(List<WordMasteryDto> wordMasteryDtoList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WordMastery partialUpdate(WordMasteryDto wordMasteryDto, @MappingTarget WordMastery wordMastery);
}
