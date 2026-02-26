package top.sakablog.nichi.service;

import org.springframework.web.multipart.MultipartFile;
import top.sakablog.nichi.model.Book;
import top.sakablog.nichi.model.dto.BookDto;

import java.util.List;

/**
 * BookService
 * <p>
 * 单词本服务接口
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface BookService {

    /* 单词本逻辑操作 */

    /**
     * 从文件导入单词本
     *
     * @param file csv文件
     * @return 导入的单词本实体
     */
    public Book importBookFromCsv(MultipartFile file);


    /* 基础 CRUD 操作 */


    /**
     * 新建单词本，名称和描述使用默认值
     *
     * @param name        单词本名称
     * @param description 单词本描述
     * @return 新建单词本的ID，创建失败返回-1
     */
    public Book createBook(String name, String description);

    /**
     * 更新单词本信息
     *
     * @param wordBook 单词本实体
     * @return 更新后的单词本实体
     */
    public Book updateBook(BookDto wordBook);

    /**
     * 根据单词本ID修改单词本名称
     *
     * @param wordBookId 单词本ID
     * @param newName    新名称
     */
    public void updateBookName(Long wordBookId, String newName);

    /**
     * 根据单词本ID修改单词本描述
     *
     * @param wordBookId 单词本ID
     * @param newDescription 新描述
     */
    public void updateBookDescription(Long wordBookId, String newDescription);

    /**
     * 根据单词本ID修改单词本等级
     *
     * @param wordBookId 单词本ID
     * @param Level      新等级
     */
    public void updateBookLevel(Long wordBookId, String Level);

    /**
     * 根据单词本ID删除单词本
     *
     * @param wordBookId 单词本ID
     */
    public void deleteBook(Long wordBookId);

    /**
     * 根据单词本ID获取单词本信息
     *
     * @param wordBookId 单词本ID
     * @return 单词本实体
     */
    public Book getWordBookById(Long wordBookId);

    /**
     * 获取所有单词本信息
     * @return 单词本实体列表
     */
    public List<Book> getAllBooks();

    /**
     * 判断单词本是否存在
     */
    public boolean existsByBookId(Long bookId);

    /**
     * 根据用户ID查询用户选择的单词本关系
     */
    public List<Book> getSelectedBooksByUserId(Long userId);
}
