package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.validators.Validator;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.core.repository.Repository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private Repository<Long, Book> repository;
    @Autowired
    private Validator<Book> validator;

    public Book addBook(Book book){
        log.trace("addBook - method entered book={}",book);
        validator.validate(book);
        Book result = repository.save(book);
        log.trace("addBook - book added book={}",book);
        log.trace("addBook - method finished");
        return result;
    }

    public Set<Book> getAllBooks() {
        log.trace("getAllBooks - method entered");
        Iterable<Book> books = repository.findAll();
        Set<Book> result = StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
        log.trace("getAllBooks - method finished. Returned: {}",result);
        return result;
    }
    public Set<Book> sort(Sort.Direction dir, String ...a ){
        log.trace("Books: sort - method entered dir={} filters={}",dir,a);
        Iterable<Book> books = repository.findAll(Sort.by(dir,a));
        Set<Book> result = StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
        log.trace("Books: sort - method finished. Returned sorted={}",result);
        return result;
    }

    public Set<Book> filterBooksByTitle(String s) {
        log.trace("filterBooksByTitle - method entered title={}",s);
        Iterable<Book> books = repository.findAll();

        Set<Book> bookSet = new HashSet<>();
        books.forEach(bookSet::add);
        bookSet.removeIf(book -> !book.getTitle().contains(s));
        log.trace("filterBooksByTitle - method finished. Returned : books={}",bookSet);
        return bookSet;
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        log.trace("updateBook - method entered: book={}", book);
        validator.validate(book);
        Book s = repository.findById(id).orElse(book);
        s.setSerialNumber(book.getSerialNumber());
        s.setTitle(book.getTitle());
        s.setAuthor(book.getAuthor());
        s.setYear(book.getYear());
        s.setPrice(book.getPrice());
        s.setInStock(book.getInStock());
        log.debug("updateBook - updated: s={}", s);
        return s;
    }

    public void deleteBook(Long bookID) throws ValidatorException {
        log.trace("deleteBook - method entered : id={}",bookID);
        try {
            repository.deleteById(bookID);
        }catch(EmptyResultDataAccessException er){
            log.trace("deleteBook - method finished. No Purchase with this ID");
            return;
        }
        log.trace("deleteBook - method finished");
    }

    public Optional<Book> findOne(Long bookID) {
        log.trace("findOne book - method entered id={}",bookID);
        log.trace("findOne book - method finished");
        return repository.findById(bookID);
    }

}
