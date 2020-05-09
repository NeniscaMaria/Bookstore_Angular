package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.web.dto.BookDto;

@Component
public class BookConverter extends BaseConverter<Book, BookDto> {
    @Override
    public Book convertDtoToModel(BookDto dto) {
        Book book = Book.builder()
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .inStock(dto.getInStock())
                .price(dto.getPrice())
                .serialNumber(dto.getSerialNumber())
                .year(dto.getYear())
                .build();
        book.setId(dto.getId());
        return book;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto bookDto = BookDto.builder()
                .author(book.getAuthor())
                .inStock(book.getInStock())
                .price(book.getPrice())
                .serialNumber(book.getSerialNumber())
                .title(book.getTitle())
                .year(book.getYear())
                .build();
        bookDto.setId(book.getId());
        return bookDto;
    }
}
