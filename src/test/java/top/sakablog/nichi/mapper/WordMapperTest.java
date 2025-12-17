package top.sakablog.nichi.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.ImportWordDto;
import top.sakablog.nichi.model.dto.WordDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
class WordMapperTest {

    // 纯单元测试直接使用工厂获取实例，无需 @Autowired
    private final WordMapper mapper = Mappers.getMapper(WordMapper.class);

    @Test
    @DisplayName("测试 Entity 转换成 WordDto")
    void shouldMapWordToWordDto() {
        // Given
        Word word = new Word();
        word.setId(100);
        word.setJapaneseWord("日本語");
        word.setKanaReading("にほんご");
        word.setMeaningCn("日语");
        word.setWordType(Word.WordType.NOUN_COMMON);
        word.setSource("标准日本语");

        // When
        WordDto dto = mapper.toDto(word);

        // Then
        assertNotNull(dto);
        assertEquals(word.getId(), dto.getId());
        assertEquals(word.getJapaneseWord(), dto.getJapaneseWord());
        assertEquals(word.getMeaningCn(), dto.getMeaningCn());
        assertEquals(word.getKanaReading(), dto.getKanaReading());
    }

    @Test
    @DisplayName("测试 ImportWordDto 转换成 Entity")
    void shouldMapImportDtoToEntity() {
        // Given
        ImportWordDto importDto = new ImportWordDto();
        importDto.setJapaneseWord("食べる");
        importDto.setKanaReading("たべる");
        importDto.setMeaningCn("吃");

        // When
        Word entity = mapper.toEntityFromImportDto(importDto);

        // Then
        assertNotNull(entity);
        assertEquals(importDto.getJapaneseWord(), entity.getJapaneseWord());
        assertEquals(importDto.getMeaningCn(), entity.getMeaningCn());
        // 验证没有设置的字段是否为 null（或默认值）
        assertNull(entity.getId());
    }

    @Test
    @DisplayName("测试集合批量转换")
    void shouldMapWordListToDtoList() {
        // Given
        Word word = new Word();
        word.setJapaneseWord("テスト");
        List<Word> list = Collections.singletonList(word);

        // When
        List<WordDto> dtoList = mapper.toDtoList(list);

        // Then
        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
        assertEquals("テスト", dtoList.get(0).getJapaneseWord());
    }

    @Test
    @DisplayName("测试输入为 null 时的处理")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(mapper.toDto(null));
        assertNull(mapper.toEntityListFromDto(null));
    }
}
