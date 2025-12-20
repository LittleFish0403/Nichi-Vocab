package top.sakablog.nichi.service;

import org.springframework.stereotype.Service;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.model.dto.UpdateWordBookDto;
import top.sakablog.nichi.model.dto.WordBookDto;

import java.io.InputStream;

/**
 * WordBookService
 * <p>
 * 单词本服务接口
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface WordBookService {

    /* 单词本逻辑操作 */

    /**
     * 从文件导入单词本
     *
     * @param fileName 文件路径 文件格式csv
     * @return 导入是否成功
     */
    public Boolean importWordBookFromCsv(String fileName) throws Exception;


    /* 基础 CRUD 操作 */


    /**
     * 新建单词本，名称和描述使用默认值
     *
     * @param name        单词本名称
     * @param description 单词本描述
     * @return 新建单词本的ID，创建失败返回-1
     */
    public WordBook newWordBook(String name, String description);

    /**
     * 更新单词本信息
     *
     * @param wordBook 单词本实体
     * @return 更新后的单词本实体
     */
    public WordBook updateWordBook(UpdateWordBookDto wordBook);

    /**
     * 根据单词本ID修改单词本名称
     *
     * @param wordBookId 单词本ID
     * @param newName    新名称
     * @return 修改是否成功
     */
    public Boolean editWordBookName(Long wordBookId, String newName);

    /**
     * 根据单词本ID修改单词本描述
     *
     * @param wordBookId 单词本ID
     * @param newDescription 新描述
     * @return 修改是否成功
     */
    public Boolean editWordBookDescription(Long wordBookId, String newDescription);

    /**
     * 根据单词本ID修改单词本等级
     *
     * @param wordBookId 单词本ID
     * @param Level      新等级
     * @return 修改是否成功
     */
    public Boolean editWordBookLevel(Long wordBookId, String Level);

    /**
     * 根据单词本ID删除单词本
     *
     * @param wordBookId 单词本ID
     * @return 删除是否成功
     */
    public Boolean deleteWordBook(Long wordBookId);
}
