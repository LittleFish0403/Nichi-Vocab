package top.sakablog.nichi.service;

import top.sakablog.nichi.model.dto.WordDto;

/**
 * WordService
 * <p>
 * 
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */public interface WordService {
     public WordDto getAllWordByWordBookId(Integer wordBookId);
}
