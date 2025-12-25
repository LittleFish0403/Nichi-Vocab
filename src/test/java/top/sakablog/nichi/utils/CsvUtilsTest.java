package top.sakablog.nichi.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import top.sakablog.nichi.model.dto.ImportWordDto;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

/**
 * CsvUtils 测试类
 * <p>
 * 测试 CsvUtils 工具类的方法
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
public class CsvUtilsTest {
    @InjectMocks
    private CsvUtils csvUtils;

    @Test
    @DisplayName("beanBuilder：解析 CSV 行数据为指定类型的对象成功")
    void whenBeanBuilderCalled_thenReturnBean() {
        // 模拟csv文件
        String mockCsvLine = """
                id,japanese_word,kana_reading,meaning_cn,word_type,source
                1,猫,ねこ,cat,NOUN,N5
                2,犬,いぬ,dog,NOUN,N5""";
        try (Reader reader = new StringReader(mockCsvLine)) {

            // 执行测试
            List<ImportWordDto> result = csvUtils.beanBuilder(reader, ImportWordDto.class);

            // 断言验证
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size(), "应该解析出 2 条数据");

            // 验证第一条数据内容
            ImportWordDto firstWord = result.get(0);
            Assertions.assertEquals("猫", firstWord.getJapaneseWord());
            Assertions.assertEquals("ねこ", firstWord.getKanaReading());
            Assertions.assertEquals("cat", firstWord.getMeaningCn());

        } catch (IOException e) {
            Assertions.fail("测试过程中不应发生 IO 异常");
        }
    }
}
