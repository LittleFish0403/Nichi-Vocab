package top.sakablog.nichi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import top.sakablog.nichi.mapper.BookMapper;
import top.sakablog.nichi.service.BookService;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@WebMvcTest(controllers = BookController.class)
public class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("集成测试：创建词书接口")
    void should_create_wordbook_successfully() throws Exception {

    }


}
