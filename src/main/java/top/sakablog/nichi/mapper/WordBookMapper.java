package top.sakablog.nichi.mapper;

import org.mapstruct.Mapper;
import top.sakablog.nichi.model.WordBook;
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
}
