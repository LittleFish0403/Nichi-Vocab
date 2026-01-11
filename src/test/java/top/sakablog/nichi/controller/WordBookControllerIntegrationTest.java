package top.sakablog.nichi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import top.sakablog.nichi.mapper.WordBookMapper;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.service.WordBookService;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@WebMvcTest(controllers = WordBookController.class)
public class WordBookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WordBookService wordBookService;

    @Mock
    private WordBookMapper wordBookMapper;

    @Test
    @DisplayName("集成测试：创建词书接口")
    void should_create_wordbook_successfully() throws Exception {

    }


}
