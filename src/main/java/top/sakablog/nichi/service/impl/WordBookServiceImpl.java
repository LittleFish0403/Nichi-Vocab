package top.sakablog.nichi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.model.WordBook;
import top.sakablog.nichi.repository.WordBookRepository;
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
@Service
public class WordBookServiceImpl implements WordBookService {
    @Autowired
    private WordBookRepository wordBookRepository;


    @Override
    public Integer newWordBook(String name, String description) {
        WordBook wordbook = new WordBook();
        wordbook.setName(name);
        wordbook.setDescription(description);
        WordBook savedWordBook = wordBookRepository.save(wordbook);
        return savedWordBook.getId();
    }

    @Override
    public Boolean deleteWordBook(Integer wordBookId) {
        return null;
    }

    @Override
    public Boolean editWordBookName(Integer wordBookId, String newName) {
        return null;
    }

    public Boolean editWordBookDescription(Integer wordBookId, String newDescription) {
        return null;
    }

}
