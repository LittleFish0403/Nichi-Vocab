package top.sakablog.nichi.service;

import org.springframework.web.multipart.MultipartFile;
import top.sakablog.nichi.model.Book;
import top.sakablog.nichi.model.dto.book.BookDto;

import java.util.List;

/**
 * BookService
 */
public interface BookService {

    Book importBookFromCsv(MultipartFile file);

    Book createBook(String name, String description);

    Book updateBook(BookDto bookDto);

    void deleteBook(Long bookId);

    Book getWordBookById(Long bookId);

    List<Book> getAllBooks();

    boolean existsByBookId(Long bookId);

    List<Book> getSelectedBooksByUserId(Long userId);
}
