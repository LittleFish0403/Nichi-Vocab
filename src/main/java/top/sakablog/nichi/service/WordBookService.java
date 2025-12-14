package top.sakablog.nichi.service;

import org.springframework.stereotype.Service;

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
    /**
     * 新建单词本，名称和描述使用默认值
     *
     * @param name        单词本名称
     * @param description 单词本描述
     * @return 新建单词本的ID，创建失败返回-1
     */
    public Integer newWordBook(String name, String description);

    /**
     * 根据单词本ID修改单词本名称
     *
     * @param wordBookId 单词本ID
     * @param newName    新名称
     * @return 修改是否成功
     */
    public Boolean editWordBookName(Integer wordBookId, String newName);

    /**
     * 根据单词本ID修改单词本描述
     *
     * @param wordBookId 单词本ID
     * @param newDescription 新描述
     * @return 修改是否成功
     */
    public Boolean editWordBookDescription(Integer wordBookId, String newDescription);

    /**
     * 根据单词本ID删除单词本
     *
     * @param wordBookId 单词本ID
     * @return 删除是否成功
     */
    public Boolean deleteWordBook(Integer wordBookId);
}
