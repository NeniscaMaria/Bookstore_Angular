package ro.ubb.catalog.core.service;

import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Book addBook(Book book);
    List<Book> getAllBooks();
    Set<Book> sort(Sort.Direction dir, String ...a );
    Set<Book> filterBooksByTitle(String s);
    Book updateBook(Long id, Book book);
    void deleteBook(Long bookID);
    Optional<Book> findOne(Long bookID);
    Book addClientToBook(Book book, Client client, String date) throws ParseException;

    List<Long> getBookIDsSortedByNumberOfPurchases();
    int getTotalStock();
}
