package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.BookWord;

/**
 * <p>
 * BookWordRepository
 * </p>
 * BookWord的数据访问层接口，提供对BookWord实体的CRUD操作
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Repository
public interface BookWordRepository extends JpaRepository<BookWord, Long> {
}
