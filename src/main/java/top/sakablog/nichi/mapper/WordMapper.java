package top.sakablog.nichi.mapper;

import org.mapstruct.*;
import top.sakablog.nichi.model.dto.word.WordDto;
import top.sakablog.nichi.model.dto.word.WordSimpleDto;
import top.sakablog.nichi.model.entity.word.Word;

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
@Mapper(componentModel = "spring", uses = {WordMapper.class})
public interface WordMapper {
    WordDto toWordDto(Word word);

    Word toWord(WordDto wordDto);

    WordSimpleDto toSimpleWordDto(Word word);

    Word toWord(WordSimpleDto wordSimpleDto);
}
