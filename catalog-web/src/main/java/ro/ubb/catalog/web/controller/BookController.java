package ro.ubb.catalog.web.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.service.BookService;
import ro.ubb.catalog.web.converter.BookConverter;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.BooksDto;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.ClientDto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
public class BookController {
    public static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;
    @Autowired
    private BookConverter bookConverter;
    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/books",method = RequestMethod.GET)
    Set<BookDto> getBooks(){
        log.trace("getBooks - method entered");
        Set<BookDto> result = bookConverter.convertModelsToDtos(bookService.getAllBooks());
        log.trace("getBooks - method finished. Returns b={}",result);
        return result;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    BookDto saveBook(@RequestBody BookDto bookDto){
        log.trace("saveBook - method entered book = {}", bookDto);
        BookDto result = bookConverter.convertModelToDto(bookService.addBook(
                bookConverter.convertDtoToModel(bookDto)));
        log.trace("saveBook - method finished client={}",result);
        return result;
    }

    @RequestMapping(value = "/books/{id}",method = RequestMethod.PUT)
    BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDto){
        log.trace("updateBook - method entered id={}, book={}",id, bookDto);
        BookDto result = bookConverter.convertModelToDto(
                bookService.updateBook(id, bookConverter.convertDtoToModel(bookDto)));
        log.trace("updateBook - method finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBook(@PathVariable Long id){
        log.trace("deleteBook - method entered id = {}",id);
        bookService.deleteBook(id);
        log.trace("deleteBook - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/books/filter/{title}", method = RequestMethod.GET)
    BooksDto filterByTitle(@PathVariable String title){
        log.trace("filterByTitle - method entered t={}",title);
        BooksDto result = new BooksDto(bookConverter.convertModelsToDtos(
                bookService.filterBooksByTitle(title)
        ));
        log.trace("filterByTitle - method finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    BookDto findOneBook(@PathVariable Long id){
        log.trace("findOneBook - method entered id={}",id);
        BookDto result = bookConverter.convertModelToDto(
                bookService.findOne(id).get()
        );
        log.trace("findOneBook finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/books/purchase/{id}/{date}",method = RequestMethod.PUT)
    BookDto addClientToBook(@PathVariable Long id, @PathVariable String date, @RequestBody ClientDto clientDto) throws ParseException {
        log.trace("addClientToBook - method entered id={}, c={},d={}",id,clientDto,date);
        BookDto bookDto = findOneBook(id);
        Book result = bookService.addClientToBook(bookConverter.convertDtoToModel(bookDto),
                clientConverter.convertDtoToModel(clientDto),date);
        log.trace("addClientToBook - method finished returns b={}",result);
        return bookConverter.convertModelToDto(result);
    }

    @RequestMapping(value = "/sort/books/{dir}", method = RequestMethod.POST)
    BooksDto sort(@PathVariable Sort.Direction dir, @RequestBody String ...a ){
        log.trace("sort - method entered dir={} args={}",dir,a);
        BooksDto result = new BooksDto(bookConverter.convertModelsToDtos(
                bookService.sort(dir,a)
        ));
        log.trace("sort finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/books/filterpurchases",method = RequestMethod.GET)
    List<BookDto> getBookIDsByNumberOfPurchases(){
        log.trace("getBookIDsByNumberOfPurchases - method entered");
        List<BookDto> result = new ArrayList<>();
        try{
            List<Long> ids = bookService.getBookIDsSortedByNumberOfPurchases();
            ids.forEach(id->{
                Book book = bookService.findOne(id).get();
                result.add(bookConverter.convertModelToDto(book));
                log.trace("getBookIDsByNumberOfPurchases - added book {}",book);
            });
        }catch (Exception ex){
            log.trace("getBookIDsByNumberOfPurchases - "+ex.getMessage()+ Arrays.toString(ex.getStackTrace()));
        }
        log.trace("getBookIDsByNumberOfPurchases - method finished c={}",result);
        return result;
    }

    @RequestMapping(value = "/books/getStock",method = RequestMethod.GET)
    int getTotalStock(){
        log.trace("getTotalStock - method entered");
        int result = bookService.getTotalStock();
        log.trace("getTotalStock - method finished c={}",result);
        return result;
    }
}
