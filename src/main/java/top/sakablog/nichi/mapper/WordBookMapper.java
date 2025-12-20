package top.sakablog.nichi.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.UpdateWordBookDto;
import top.sakablog.nichi.model.dto.WordBookDto;

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
public interface WordBookMapper {
    WordBookDto toWordBookDto(WordBook wordBook);

    WordBook toWordBook(WordBookDto wordBookDto);

    List<WordBookDto> toWordBookDtoList(List<WordBook> wordBooks);

    List<WordBook> toWordBookList(List<WordBookDto> wordBookDtos);

    WordBook toEntity(UpdateWordBookDto updateWordBookDto);

    UpdateWordBookDto toDto(WordBook wordBook);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WordBook partialUpdate(UpdateWordBookDto updateWordBookDto, @MappingTarget WordBook wordBook);
}
