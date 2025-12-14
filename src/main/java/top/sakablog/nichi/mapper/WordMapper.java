package top.sakablog.nichi.mapper;

import org.mapstruct.Mapper;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.ImportWordDto;
import top.sakablog.nichi.model.dto.WordDto;

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
public interface WordMapper {
    WordDto toDto(Word word);
    ImportWordDto toImportWordDto(Word word);

    Word toEntityFromDto(WordDto wordDto);
    Word toEntityFromImportDto(ImportWordDto importWordDto);

    List<WordDto> toDtoList(List<Word> wordList);
    List<ImportWordDto> toImportWordDtoList(List<Word> wordList);
    List<Word> toEntityListFromDto(List<WordDto> wordDtoList);
    List<Word> toEntityListFromImportDto(List<ImportWordDto> importWord);
}
