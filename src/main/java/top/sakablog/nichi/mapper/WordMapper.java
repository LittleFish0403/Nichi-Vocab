package top.sakablog.nichi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.WordImportDto;
import top.sakablog.nichi.model.dto.WordDto;
import top.sakablog.nichi.model.enums.WordType;

import java.util.List;

/**
 * WordMapper
 * <p>
 * 实现Entity和DTO之间的相互转化
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface WordMapper {
    /**
     * Entity转DTO
     */
    WordDto toDto(Word word);
    WordImportDto toImportWordDto(Word word);


    /**
     * DTO转Entity
     */
    @Mapping(target = "wordType", source = "wordType", qualifiedByName = "mapToWordType")
    Word toEntityFromDto(WordDto wordDto);
    @Mapping(target = "wordType", source = "wordType", qualifiedByName = "mapToWordType")
    Word toEntityFromImportDto(WordImportDto wordImportDto);

    /**
     * ListEntity转ListDTO 和 ListDTO转ListEntity
     */
    List<WordDto> toDtoList(List<Word> wordList);
    List<WordImportDto> toImportWordDtoList(List<Word> wordList);
    List<Word> toEntityListFromDto(List<WordDto> wordDtoList);
    List<Word> toEntityListFromImportDto(List<WordImportDto> importWord);

    @Named("mapToWordType")
    default WordType mapToWordType(String typeStr) {
        return WordType.fromString(typeStr);
    }
}
