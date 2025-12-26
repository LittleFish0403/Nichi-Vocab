package top.sakablog.nichi.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.mapper.WordMapperImpl;
import top.sakablog.nichi.model.ListWord;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.ImportWordDto;
import top.sakablog.nichi.model.dto.UpdateWordBookDto;
import top.sakablog.nichi.model.enums.WordType;
import top.sakablog.nichi.repository.WordBookRepository;
import top.sakablog.nichi.service.ListWordService;
import top.sakablog.nichi.service.WordService;
import top.sakablog.nichi.service.impl.WordBookServiceImpl;
import top.sakablog.nichi.utils.CsvUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
@ExtendWith(MockitoExtension.class)
public class WordBookServiceTest {
    @Mock
    private WordBookRepository wordBookRepository;
    @Mock
    private WordService wordService;
    @Mock
    private ListWordService listWordService;
    @Mock
    private CsvUtils csvUtils;

    @Spy
    private WordMapper wordMapper = new WordMapperImpl();

    @InjectMocks
    private WordBookServiceImpl wordBookService;

    @Captor
    private ArgumentCaptor<List<Word>> wordListCaptor;

    @Captor
    private ArgumentCaptor<ListWord> listWordCaptor;

    @Captor
    private ArgumentCaptor<WordBook> wordBookCaptor;


    @Nested
    @DisplayName("单词本 CRUD 操作测试")
    class WordBookCRUDTests{
        @Test
        @DisplayName("newWordBook：成功创建单词本")
        void whenNewWordBookCalled_shouldCreateWordBookSuccessfully() {
            String name = "新建单词本";
            String description = "这是一个新建的单词本";

            WordBook mockSavedResult = new WordBook()
                    .setId(100L)
                    .setName(name)
                    .setDescription(description)
                    .setCount(0);
            when(wordBookRepository.save(any(WordBook.class))).thenReturn(mockSavedResult);
            WordBook result = wordBookService.newWordBook(name, description);

            // 验证结果
            assertNotNull(result);
            assertEquals(name, result.getName());
            assertEquals(description, result.getDescription());
            assertEquals(0, result.getCount());

            // 验证行为： save 方法被调用一次
            verify(wordBookRepository, times(1)).save(any(WordBook.class));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "  "}) // 测试空串、空格
        @NullSource
        @DisplayName("newWordBook：名字为空时应抛出异常")
        void whenNameIsEmpty_shouldThrowException(String invalidName) {
            // 1. Arrange & Act & Assert (合在一起写)
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                wordBookService.newWordBook(invalidName, "有效描述");
            });

            // 2. 进一步验证：异常消息是否符合预期？
            assertEquals("词本名称不能为空", exception.getMessage());

            // 3. 验证行为：由于参数校验就失败了，Repository 不应该被调用
            verify(wordBookRepository, never()).save(any());
        }

        @Test
        @DisplayName("deleteWordBook：成功删除单词本")
        void whenDeleteWordBookCalled_shouldDeleteWordBookSuccessfully() {
            Long wordBookId = 1L;

            // 模拟 Repository 行为
            when(wordBookRepository.existsById(wordBookId)).thenReturn(true);
            doNothing().when(wordBookRepository).deleteById(wordBookId);

            // 调用被测试方法
            wordBookService.deleteWordBook(wordBookId);

            // 验证行为： deleteById 方法被调用一次
            verify(wordBookRepository, times(1)).deleteById(wordBookId);
        }

        @Test
        @DisplayName("deleteWordBook：删除不存在的单词本应抛出异常")
        void whenDeleteNonExistentWordBook_shouldThrowException() {
            Long nonExistentId = 999L;
            // 模拟 Repository 行为：该 ID 不存在
            when(wordBookRepository.existsById(nonExistentId)).thenReturn(false);
            // 1. Arrange & Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                wordBookService.deleteWordBook(nonExistentId);
            });
            // 2. 进一步验证：异常消息是否符合预期？
            assertEquals("删除失败：词书 ID [" + nonExistentId + "] 不存在", exception.getMessage());
            // 3. 验证行为：由于找不到资源
            verify(wordBookRepository, never()).deleteById(nonExistentId);
        }

        @Test
        @DisplayName("updateWordBook：成功更新单词本信息")
        void whenUpdateWordBookCalled_shouldUpdateWordBookSuccessfully() {
            // 准备测试数据
            WordBook mockSavedResult = new WordBook()
                    .setId(100L)
                    .setName("旧名称")
                    .setDescription("旧描述")
                    .setCount(0)
                    .setLevel("旧等级");
            UpdateWordBookDto mockSavedResult2 = new UpdateWordBookDto(100L, "新名称", "新等级", "新描述");

            // 打桩
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(WordBook.class))).thenReturn(mockSavedResult);

            // 调用被测试方法
            wordBookService.updateWordBook(mockSavedResult2);

            // 验证结果
            assertEquals(100L, mockSavedResult.getId());
            assertEquals("新名称", mockSavedResult.getName());
            assertEquals("新描述", mockSavedResult.getDescription());
            assertEquals("新等级", mockSavedResult.getLevel());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(WordBook.class));
        }

        @Test
        @DisplayName("editWordBookName：成功修改单词本名称")
        void editWordBookName_shouldEditNameSuccessfully() {
            WordBook mockSavedResult = new WordBook()
                    .setId(100L)
                    .setName("旧名称")
                    .setDescription("描述")
                    .setCount(0);
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(WordBook.class))).thenReturn(mockSavedResult);

            wordBookService.editWordBookName(100L, "新名称");

            // 验证结果
            assertEquals("新名称", mockSavedResult.getName());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(WordBook.class));
        }

        @Test
        @DisplayName("editWordBookLevel：成功修改单词本等级")
        void editWordBookLevel_shouldEditLevelSuccessfully() {
            WordBook mockSavedResult = new WordBook()
                    .setId(100L)
                    .setName("名称")
                    .setDescription("描述")
                    .setCount(0)
                    .setLevel("旧等级");
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(WordBook.class))).thenReturn(mockSavedResult);

            wordBookService.editWordBookLevel(100L, "新等级");

            // 验证结果
            assertEquals("新等级", mockSavedResult.getLevel());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(WordBook.class));
        }

        @Test
        @DisplayName("editWordBookDescription：成功修改单词本描述")
        void editWordBookDescription_shouldEditDescriptionSuccessfully() {
            WordBook mockSavedResult = new WordBook()
                    .setId(100L)
                    .setName("名称")
                    .setDescription("旧描述")
                    .setCount(0);
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(WordBook.class))).thenReturn(mockSavedResult);

            wordBookService.editWordBookDescription(100L, "新描述");

            // 验证结果
            assertEquals("新描述", mockSavedResult.getDescription());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(WordBook.class));
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"标日初级单词表.csv"})
    @DisplayName("importWordBookFromCsv：全流程逻辑验证")
    void whenImportWordBookFromCsv_shouldImportWordBookFromCsvSuccessfully(String testFileName) throws Exception {

        WordBook mockSavedWordBook = new WordBook()
                .setId(1L)
                .setName("标日初级单词表")
                .setDescription("从CSV导入的单词本")
                .setCount(1);

        Word mockSavedWord = new Word()
                .setId(1L)
                .setJapaneseWord("テスト")
                .setKanaReading("テスト")
                .setMeaningCn("测试")
                .setWordType(WordType.NOUN_COMMON)
                .setSource("标日初级");

        ListWord mockListWord = new ListWord()
                .setWord(mockSavedWord)
                .setWordBook(mockSavedWordBook);

        ImportWordDto mockSavedImportWordDto = wordMapper.toImportWordDto(mockSavedWord);

        List<ImportWordDto> mockSavedImportWordDtoList = new ArrayList<>();
        mockSavedImportWordDtoList.add(mockSavedImportWordDto);
        List<Word> mockSavedWordList = new ArrayList<>();
        mockSavedWordList.add(mockSavedWord);
        List<ListWord> mockListWordList = new ArrayList<>();
        mockListWordList.add(mockListWord);


        when(wordBookRepository.save(any(WordBook.class))).thenReturn(mockSavedWordBook);
        when(wordService.saveAllWords(any())).thenReturn(mockSavedWordList);
        when(listWordService.saveAllListWord(anyList(), any(WordBook.class))).thenReturn(mockListWordList);
        when(csvUtils.beanBuilder(any(Path.class), eq(ImportWordDto.class))).thenReturn(mockSavedImportWordDtoList);

        // 开始测试
        wordBookService.importWordBookFromCsv(testFileName);

        // 验证行为：各个依赖方法是否被调用
        verify(wordBookRepository, times(1)).save(any(WordBook.class));
        verify(wordService, times(1)).saveAllWords(wordListCaptor.capture());
        verify(listWordService, times(1)).saveAllListWord(anyList(), wordBookCaptor.capture());
        verify(csvUtils, times(1)).beanBuilder(any(Path.class), eq(ImportWordDto.class));

        // 捕获参数
        List<Word> capturedWordList = wordListCaptor.getValue();

        // 验证最终结果
        assertEquals(1, capturedWordList.size());
        assertEquals("テスト", capturedWordList.get(0).getJapaneseWord());
        assertEquals("测试", capturedWordList.get(0).getMeaningCn());
    }

    @Test
    @DisplayName("importWordBookFromCsv：文件不存在时抛出异常")
    void whenImportWordBookFromCsvWithNonExistentFile_shouldThrowException() {
        String invalidFileName = "不存在的文件.csv";
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            wordBookService.importWordBookFromCsv(invalidFileName);
        });

        // 验证异常消息
        assertEquals("文件不存在: " + invalidFileName, exception.getMessage());
    }

}
