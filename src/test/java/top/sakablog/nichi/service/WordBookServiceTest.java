package top.sakablog.nichi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.ImportWordDto;
import top.sakablog.nichi.repository.WordBookRepository;
import top.sakablog.nichi.service.ListWordService;
import top.sakablog.nichi.service.WordService;
import top.sakablog.nichi.service.impl.WordBookServiceImpl;
import top.sakablog.nichi.utils.CsvUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public class WordBookServiceTest {
    @Mock
    private WordBookRepository wordBookRepository;
    @Mock
    private WordMapper wordMapper;
    @Mock
    private WordService wordService;
    @Mock
    private ListWordService listWordService;
    @Mock
    private CsvUtils csvUtils; // 现在可以轻松 Mock 了

    @InjectMocks
    private WordBookServiceImpl wordBookService;

    @Test
    @DisplayName("测试从CSV导入单词本：全流程逻辑验证")
    void whenImportWordBookFromCsv_shouldImportWordBookFromCsvSuccessfully() throws Exception {
        // 1. Given: 模拟数据
        String testFileName = "标日初级单词表.csv";

        WordBook mockBook = new WordBook().setId(1l).setName(testFileName);

        List<ImportWordDto> mockImportDtos = new ArrayList<>();
        mockImportDtos.add(new ImportWordDto()); // 添加一个假数据

        List<Word> mockWords = new ArrayList<>();
        mockWords.add(new Word().setJapaneseWord("テスト"));
    }
}
