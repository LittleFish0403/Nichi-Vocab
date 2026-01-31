package top.sakablog.nichi.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.WordImportDto;
import top.sakablog.nichi.model.dto.WordDto;
import top.sakablog.nichi.model.enums.WordType;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * WordMapperTest
 * <p>
 * 测试 WordMapper 的各种转换方法
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
        word.setId(100l);
        word.setJapaneseWord("日本語");
        word.setKanaReading("にほんご");
        word.setMeaningCn("日语");
        word.setWordType(WordType.NOUN_COMMON);
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
    @DisplayName("测试 WordImportDto 转换成 Entity")
    void shouldMapImportDtoToEntity() {
        // Given
        WordImportDto importDto = new WordImportDto();
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
