package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.Book;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN UserBookProgress p ON p.book.id = b.id WHERE p.id = :userBookProgressId")
    List<Book> findByUserBookProgressId(@Param("progressId") Long userBookProgressId);
}
