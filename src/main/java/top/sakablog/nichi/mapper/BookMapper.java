package top.sakablog.nichi.mapper;

import org.mapstruct.Mapper;
import top.sakablog.nichi.model.Book;
import top.sakablog.nichi.model.dto.BookDto;

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
@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toWordBookDto(Book book);

    Book toWordBook(BookDto bookDto);

    List<BookDto> toWordBookDtoList(List<Book> books);

    List<Book> toWordBookList(List<BookDto> bookDtos);
}
