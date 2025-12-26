package top.sakablog.nichi.service;

import top.sakablog.nichi.model.Word;
import top.sakablog.nichi.model.dto.WordDto;

import java.util.List;

/**
 * WordService
 * <p>
 * 
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */public interface WordService {


      //=== CRUD FIND ===//

     /**
      * 根据单词本ID查找对应单词
      * @param wordBookId 单词本ID
      * @return 单词DTO对象
      */
      public List<Word> findAllWordsByWordBookId(Long wordBookId);

      //=== CRUD SAVE ===//

     /**
      * 保存单词
      *
      * @param word 单词对象
      * @return 保存后的单词对象
      */
     public Word saveWord(Word word);

     /**
      * 批量保存单词
      *
      * @param words 单词对象列表
      * @return 保存后的单词对象列表
      */
     public List<Word> saveAllWords(List<Word> words);

     /**
      * 根据单词本ID保存单词
      *
      * @param wordBookId 单词本ID
      * @param word       单词对象
      * @return 保存是否成功
      */
     public Boolean saveWordByWordBookId(Long wordBookId, Word word);
}
