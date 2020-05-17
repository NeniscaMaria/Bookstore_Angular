package ro.ubb.catalog.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.ClientsDto;

import java.util.HashSet;
import java.util.Set;

@Component
public class BookConverter extends BaseConverter<Book, BookDto> {
    @Autowired
    private ClientConverter clientConverter;
    @Override
    public Book convertDtoToModel(BookDto dto) {
        Book book = Book.builder()
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .inStock(dto.getInStock())
                .price(dto.getPrice())
                .serialNumber(dto.getSerialNumber())
                .year(dto.getYear())
                .clients(new HashSet<>())
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
