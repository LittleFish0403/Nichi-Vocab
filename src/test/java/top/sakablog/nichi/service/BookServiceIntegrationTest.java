package top.sakablog.nichi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.repository.WordRepository;

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
@SpringBootTest // 启动完整 Spring 上下文
 // 确保每个测试方法后数据回滚
public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private WordBookRepository wordBookRepository;

    @Autowired
    private WordRepository wordRepository;

    @Test
    @DisplayName("集成测试：创建词书并修改信息")
    void testCreateAndEditWordBook() {
        // 1. 测试创建
        Book savedBook = bookService.createBook("新标日初级", "一本好书");
        assertNotNull(savedBook.getId());
        assertEquals("新标日初级", savedBook.getName());

        // 2. 测试修改名称
        bookService.updateBookName(savedBook.getId(), "新标日高级");

        // 3. 验证数据库中确实更新了
        Book updatedBook = wordBookRepository.findById(savedBook.getId()).orElse(null);
        assertNotNull(updatedBook);
        assertEquals("新标日高级", updatedBook.getName());
    }

//    @Test
//    @DisplayName("集成测试：从CSV文件完整导入流程")
//    @Transactional
//    void testImportWordBookFromCsvFlow() throws Exception {
//        // 注意：请确保 src/main/resources 下有一个合法的 word_template.csv
//        // 如果文件不存在，此测试会抛出 URI is not hierarchical 异常
//        String fileName = "标日初级单词表.csv";
//
//        // 执行黑盒操作
//       bookService.importBookFromCsv(fileName);
//
//        // 验证数据库中是否产生了数据
//        List<Book> books = wordBookRepository.findAll();
//        assertFalse(books.isEmpty(), "词书表不应为空");
//
//        List<Word> words = wordRepository.findAll();
//        assertFalse(words.isEmpty(), "单词表不应为空");
//
//        System.out.println("导入成功的单词数量: " + words.size());
//    }
}
