package top.sakablog.nichi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.mapper.WordMapper;
import top.sakablog.nichi.mapper.WordMapperImpl;
import top.sakablog.nichi.model.Book;
import top.sakablog.nichi.model.BookWord;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.BookDto;
import top.sakablog.nichi.model.dto.WordImportDto;
import top.sakablog.nichi.service.impl.BookServiceImpl;
import top.sakablog.nichi.utils.CsvUtils;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
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
public class BookServiceTest {
    @Mock
    private WordBookRepository wordBookRepository;
    @Mock
    private WordService wordService;
    @Mock
    private BookWordService bookWordService;
    @Mock
    private CsvUtils csvUtils;

    @Spy
    private WordMapper wordMapper = new WordMapperImpl();

    @InjectMocks
    private BookServiceImpl wordBookService;

    @Captor
    private ArgumentCaptor<List<Word>> wordListCaptor;

    @Captor
    private ArgumentCaptor<BookWord> listWordCaptor;

    @Captor
    private ArgumentCaptor<Book> wordBookCaptor;


    @Nested
    @DisplayName("单词本 CRUD 操作测试")
    class BookCRUDTests {
        @Test
        @DisplayName("createBook：成功创建单词本")
        void whenNewWordBookCalled_shouldCreateWordBookSuccessfully() {
            String name = "新建单词本";
            String description = "这是一个新建的单词本";

            Book mockSavedResult = new Book()
                    .setId(100L)
                    .setName(name)
                    .setDescription(description)
                    .setCount(0);
            when(wordBookRepository.save(any(Book.class))).thenReturn(mockSavedResult);
            Book result = wordBookService.createBook(name, description);

            // 验证结果
            assertNotNull(result);
            assertEquals(name, result.getName());
            assertEquals(description, result.getDescription());
            assertEquals(0, result.getCount());

            // 验证行为： save 方法被调用一次
            verify(wordBookRepository, times(1)).save(any(Book.class));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "  "}) // 测试空串、空格
        @NullSource
        @DisplayName("createBook：名字为空时应抛出异常")
        void whenNameIsEmpty_shouldThrowException(String invalidName) {
            // 1. Arrange & Act & Assert (合在一起写)
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                wordBookService.createBook(invalidName, "有效描述");
            });

            // 2. 进一步验证：异常消息是否符合预期？
            assertEquals("词本名称不能为空", exception.getMessage());

            // 3. 验证行为：由于参数校验就失败了，Repository 不应该被调用
            verify(wordBookRepository, never()).save(any());
        }

        @Test
        @DisplayName("deleteBook：成功删除单词本")
        void whenDeleteWordBookCalled_shouldDeleteWordBookSuccessfully() {
            Long wordBookId = 1L;

            // 模拟 Repository 行为
            when(wordBookRepository.existsById(wordBookId)).thenReturn(true);
            doNothing().when(wordBookRepository).deleteById(wordBookId);

            // 调用被测试方法
            wordBookService.deleteBook(wordBookId);

            // 验证行为： deleteById 方法被调用一次
            verify(wordBookRepository, times(1)).deleteById(wordBookId);
        }

        @Test
        @DisplayName("deleteBook：删除不存在的单词本应抛出异常")
        void whenDeleteNonExistentWordBook_shouldThrowException() {
            Long nonExistentId = 999L;
            // 模拟 Repository 行为：该 ID 不存在
            when(wordBookRepository.existsById(nonExistentId)).thenReturn(false);
            // 1. Arrange & Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                wordBookService.deleteBook(nonExistentId);
            });
            // 2. 进一步验证：异常消息是否符合预期？
            assertEquals("删除失败：词书 ID [" + nonExistentId + "] 不存在", exception.getMessage());
            // 3. 验证行为：由于找不到资源
            verify(wordBookRepository, never()).deleteById(nonExistentId);
        }

        @Test
        @DisplayName("updateBook：成功更新单词本信息")
        void whenUpdateWordBookCalled_shouldUpdateWordBookSuccessfully() {
            // 准备测试数据
            Book mockSavedResult = new Book()
                    .setId(100L)
                    .setName("旧名称")
                    .setDescription("旧描述")
                    .setCount(0)
                    .setLevel("旧等级");
            BookDto mockSavedResult2 = new BookDto(100L, "新名称", "新等级", 0, "新描述");

            // 打桩
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(Book.class))).thenReturn(mockSavedResult);

            // 调用被测试方法
            wordBookService.updateBook(mockSavedResult2);

            // 验证结果
            assertEquals(100L, mockSavedResult.getId());
            assertEquals("新名称", mockSavedResult.getName());
            assertEquals("新描述", mockSavedResult.getDescription());
            assertEquals("新等级", mockSavedResult.getLevel());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(Book.class));
        }

        @Test
        @DisplayName("updateBookName：成功修改单词本名称")
        void editWordBookName_shouldEditNameSuccessfully() {
            Book mockSavedResult = new Book()
                    .setId(100L)
                    .setName("旧名称")
                    .setDescription("描述")
                    .setCount(0);
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(Book.class))).thenReturn(mockSavedResult);

            wordBookService.updateBookName(100L, "新名称");

            // 验证结果
            assertEquals("新名称", mockSavedResult.getName());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(Book.class));
        }

        @Test
        @DisplayName("updateBookLevel：成功修改单词本等级")
        void editWordBookLevel_shouldEditLevelSuccessfully() {
            Book mockSavedResult = new Book()
                    .setId(100L)
                    .setName("名称")
                    .setDescription("描述")
                    .setCount(0)
                    .setLevel("旧等级");
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(Book.class))).thenReturn(mockSavedResult);

            wordBookService.updateBookLevel(100L, "新等级");

            // 验证结果
            assertEquals("新等级", mockSavedResult.getLevel());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(Book.class));
        }

        @Test
        @DisplayName("updateBookDescription：成功修改单词本描述")
        void editWordBookDescription_shouldEditDescriptionSuccessfully() {
            Book mockSavedResult = new Book()
                    .setId(100L)
                    .setName("名称")
                    .setDescription("旧描述")
                    .setCount(0);
            when(wordBookRepository.findById(100L)).thenReturn(Optional.of(mockSavedResult));
            when(wordBookRepository.save(any(Book.class))).thenReturn(mockSavedResult);

            wordBookService.updateBookDescription(100L, "新描述");

            // 验证结果
            assertEquals("新描述", mockSavedResult.getDescription());

            // 验证行为： findById 和 save 方法各被调用一次
            verify(wordBookRepository, times(1)).findById(100L);
            verify(wordBookRepository, times(1)).save(any(Book.class));
        }

        @Test
        @DisplayName("getWordBookById：成功获取单词本信息")
        void whenGetWordBookByIdCalled_shouldReturnWordBookSuccessfully() {
            Long wordBookId = 1L;
            Book mockBook = new Book()
                    .setId(wordBookId)
                    .setName("测试单词本")
                    .setDescription("这是一个测试单词本")
                    .setCount(10);
            when(wordBookRepository.findById(wordBookId)).thenReturn(Optional.of(mockBook));
            Book result = wordBookService.getWordBookById(wordBookId);
            // 验证结果
            assertNotNull(result);
            assertEquals(wordBookId, result.getId());
            assertEquals("测试单词本", result.getName());
            assertEquals("这是一个测试单词本", result.getDescription());
            assertEquals(10, result.getCount());
            // 验证行为： findById 方法被调用一次
            verify(wordBookRepository, times(1)).findById(wordBookId);
        }

        @Test
        @DisplayName("getAllBooks：成功获取所有单词本信息")
        void whenGetAllWordBooksCalled_shouldReturnAllWordBooksSuccessfully() {
            List<Book> mockBookList = Arrays.asList(
                    new Book().setId(1L).setName("单词本1").setDescription("描述1").setCount(5),
                    new Book().setId(2L).setName("单词本2").setDescription("描述2").setCount(10)
            );
            when(wordBookRepository.findAll()).thenReturn(mockBookList);
            List<Book> result = wordBookService.getAllBooks();
            // 验证结果
            assertNotNull(result);
            assertEquals(2, result.size());
            // 验证行为： findAll 方法被调用一次
            verify(wordBookRepository, times(1)).findAll();
        }
    }


    @Test // 注意：MultipartFile 无法直接从 ValueSource 注入，建议改用普通 @Test
    @DisplayName("importBookFromCsv：全流程逻辑验证")
    void whenImportWordBookFromCsv_shouldImportBookFromCsvSuccessfully() throws Exception {
        // 1. 模拟一个上传文件 (MockMultipartFile)
        // 参数含义：表单参数名(file), 原始文件名, 内容类型, 内容字节
        String fileName = "标日初级单词表.csv";
        MockMultipartFile testFile = new MockMultipartFile(
                "file",
                fileName,
                "text/csv",
                "word,reading,meaning\nテスト,テスト,测试".getBytes(StandardCharsets.UTF_8)
        );

        // 2. 准备 Mock 数据 (保持你原有的逻辑)
        Book mockSavedBook = new Book()
                .setId(1L)
                .setName(fileName)
                .setCount(1);

        Word mockSavedWord = new Word()
                .setJapaneseWord("テスト")
                .setMeaningCn("测试");

        List<WordImportDto> mockImportDtoList = new ArrayList<>();
        mockImportDtoList.add(new WordImportDto()); // 简化 mock 内容

        List<Word> mockSavedWordList = List.of(mockSavedWord);

        // 3. 打桩 (Stubbing) - 注意参数类型的变化！
        when(wordBookRepository.save(any(Book.class))).thenReturn(mockSavedBook);
        // 关键点：csvUtils 现在接收的是 Reader 而不是 Path
        when(csvUtils.beanBuilder(any(Reader.class), eq(WordImportDto.class))).thenReturn(mockImportDtoList);
        when(wordMapper.toEntityListFromImportDto(anyList())).thenReturn(mockSavedWordList);
        when(wordService.saveAllWords(any())).thenReturn(mockSavedWordList);

        // 4. 执行测试
        wordBookService.importBookFromCsv(testFile);

        // 5. 验证行为
        verify(wordBookRepository, times(1)).save(any(Book.class));
        // 验证 csvUtils 是否收到了 Reader 类型的参数
        verify(csvUtils, times(1)).beanBuilder(any(Reader.class), eq(WordImportDto.class));
        verify(wordService, times(1)).saveAllWords(wordListCaptor.capture());

        // 6. 验证最终结果
        List<Word> capturedWordList = wordListCaptor.getValue();
        assertEquals(1, capturedWordList.size());
        assertEquals("テスト", capturedWordList.get(0).getJapaneseWord());
    }

}
