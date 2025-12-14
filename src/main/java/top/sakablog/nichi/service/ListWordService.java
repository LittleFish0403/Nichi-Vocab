package top.sakablog.nichi.service;

import top.sakablog.nichi.model.ListWord;
import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.WordBook;

import java.util.List;

/**
 * ListWordService
 * <p>
 * 单词和单词本关联服务
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface ListWordService {

    /**
     * 保存单词和单词本的关联
     *
     * @param word     单词对象
     * @param wordBook 单词本对象
     * @return 保存后的ListWord对象
     */
    public ListWord saveListWord(Word word, WordBook wordBook);

    /**
     * 批量保存单词和单词本的关联
     *
     * @param listWords ListWord对象列表
     * @return 保存后的ListWord对象列表
     */
    public List<ListWord> saveAllListWord(List<Word> words, WordBook wordBook);
}
