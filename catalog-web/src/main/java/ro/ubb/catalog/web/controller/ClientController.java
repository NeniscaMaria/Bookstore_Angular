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
import ro.ubb.catalog.core.service.ClientService;
import ro.ubb.catalog.web.converter.BookConverter;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.BooksDto;
import ro.ubb.catalog.web.dto.ClientDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientConverter clientConverter;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookConverter bookConverter;

    @RequestMapping(value = "/clients",method = RequestMethod.GET)
    Set<ClientDto> getClients(){
        log.trace("getClients - method entered");
        List<Client> clients = clientService.getAllClients();
        System.out.println("hello");
        try {
            Set<ClientDto> result = clientConverter.convertModelsToDtos(clients);
            log.trace("getClients - method finished");
            return result;
        }catch(StackOverflowError se){
            System.out.println("stack overflow go brr");
        }
        return null;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto){
        log.trace("saveClient - method entered client = {}",clientDto);
        ClientDto result = clientConverter.convertModelToDto(clientService.addClient(
                clientConverter.convertDtoToModel(clientDto)
        ));
        log.trace("saveClient - method finished client={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/{id}",method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto){
        log.trace("updateClient - method entered id={}, client={}",id,clientDto);
        ClientDto result = clientConverter.convertModelToDto(
                clientService.updateClient(id, clientConverter.convertDtoToModel(clientDto))
        );
        log.trace("updateClient - method finished returns client={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        log.trace("deleteClient - method entered id = {}",id);
        clientService.removeClient(id);
        log.trace("deleteClient - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/filter/{name}", method = RequestMethod.GET)
    Set<ClientDto> filterByName(@PathVariable String name){
        log.trace("filterByName - method entered name={}",name);
        Set<ClientDto> result = clientConverter.convertModelsToDtos(
                clientService.filterClientsByName(name)
        );
        log.trace("filterByName - method finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/find/{id}", method = RequestMethod.GET)
    ClientDto findOneClient(@PathVariable Long id){
        log.trace("findOneClient - method entered id={}",id);
        ClientDto result = clientConverter.convertModelToDto(clientService.findOneClient(id).get());
        log.trace("findOneClient finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/purchase/{id}/{date}",method = RequestMethod.PUT)
    ClientDto addBookToClient(@PathVariable Long id,@PathVariable String date, @RequestBody BookDto bookDto){
        log.trace("addBookToClient - method entered id={}, b={}",id,bookDto);
        ClientDto clientDto = findOneClient(id);
        Client result = clientService.addBookToClient(clientConverter.convertDtoToModel(clientDto),
                bookConverter.convertDtoToModel(bookDto),date);
        log.trace("addBookToClient - method finished returns c={}",result);
        return clientConverter.convertModelToDto(result);
    }

    @RequestMapping(value = "/clients/purchase/remove/{id}",method = RequestMethod.PUT)
    int removePurchase(@PathVariable Long id, @RequestBody BookDto bookDto){
        log.trace("removePurchase - method entered id={}, b={}",id,bookDto);
        Client client = clientService.findOneClient(id).get();
        int result = clientService.removePurchase(client,bookConverter.convertDtoToModel(bookDto));
        log.trace("removePurchase - method finished c={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/purchase/get/{id}",method = RequestMethod.PUT)
    Set<BookDto> getPurchases(@PathVariable Long id){
        log.trace("getPurchases - method entered ");
        Set<BookDto> result = bookConverter.convertModelsToDtos(clientService.getBooks(id));
        log.trace("getPurchases - method finished r={}",result);
        return result;
    }

}
