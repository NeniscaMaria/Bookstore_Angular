package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.validators.Validator;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.core.repository.BookRepository;
import ro.ubb.catalog.core.repository.Repository;

import javax.persistence.EntityManagerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookRepository repository;
    @Autowired
    private Validator<Book> validator;
    @Value("${db.method}")
    private String method;

    public Book addBook(Book book){
        log.trace("addBook - method entered book={}",book);
        validator.validate(book);
        Book result = repository.save(book);
        log.trace("addBook - book added book={}",book);
        log.trace("addBook - method finished");
        return result;
    }

    @Transactional
    public List<Book> getAllBooks() {
        log.trace("getAllBooks - method entered");
        List<Book> result = repository.findAll();
        log.trace("getAllBooks - method finished. Returned: {}",result);
        return result;
    }

    @Transactional
    public Set<Book> sort(Sort.Direction dir, String ...a ){
        log.trace("Books: sort - method entered dir={} filters={}",dir,a);
        Iterable<Book> books = repository.findAll(Sort.by(dir,a));
        Set<Book> result = StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
        log.trace("Books: sort - method finished. Returned sorted={}",result);
        return result;
    }

    @Transactional
    public Set<Book> filterBooksByTitle(String s) {
        log.trace("filterBooksByTitle - method entered title={}",s);
        Set<Book> bookSet = repository.filterBooksByTitle(s);
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
        log.trace("updateBook - updated: s={}", s);
        return s;
    }

    public Book addClientToBook(Book book, Client client, String date) {
        log.trace("addClientToBook - method entered: book={} c={} d={}", book,client,date);
        try {
            book.addDate(client, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            book.addDate(client,new Date());
        }
        repository.save(book);
        log.trace("addClientToBook - finished: s={}", book);
        return book;
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

    @Transactional
    public Optional<Book> findOne(Long bookID) {
        log.trace("findOne book - method entered id={}",bookID);
        Optional<Book> result = repository.findById(bookID);
        log.trace("findOne book - method finished b={}",result);
        return result;
    }

    @Override
    public List<Long> getBookIDsSortedByNumberOfPurchases() {
        log.trace("getBookIDsSortedByNumberOfPurchases - method entered m={}",method);
        List<Long> result = new ArrayList<>();
        try {
            if(method.equals("JPQL"))
                result = repository.getBookIDsSortedByNumberOfPurchasesJPQL();
            else if(method.equals("Criteria"))
                result = repository.getBooksIDsSortedByNumberOfPurchasesCriteria();
            else if(method.equals("Native"))
                result = repository.getBooksIDsSortedByNumberOfPurchasesNative();
        }catch(Exception ex){
            log.trace("error occurred "+ ex.getMessage() + " " + ex.getCause());
        }
        log.trace("getBookIDsSortedByNumberOfPurchases - method finished r={}",result);
        return result;
    }

    @Override
    public int getTotalStock() {
        log.trace("getTotalStock - method entered m={}",method);
        int result = -1;
        try {
            if(method.equals("JPQL"))
                result = repository.getTotalStockJPQL();
            else if(method.equals("Criteria"))
                result = repository.getTotalStockCriteria();
            else if(method.equals("Native"))
                result = repository.getTotalStockNative();
        }catch(Exception ex){
            log.trace("error occurred "+ ex.getMessage() + " " + ex.getCause());
        }
        log.trace("getTotalStock - method finished r={}",result);
        return result;
    }
}
